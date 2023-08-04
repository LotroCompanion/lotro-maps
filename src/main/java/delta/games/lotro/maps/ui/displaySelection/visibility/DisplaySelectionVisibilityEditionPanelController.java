package delta.games.lotro.maps.ui.displaySelection.visibility;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.panels.AbstractPanelController;
import delta.common.ui.swing.selection.SelectionManager;
import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.tables.panel.FilterUpdateListener;
import delta.games.lotro.maps.data.displaySelection.DisplaySelection;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionItem;
import delta.games.lotro.maps.ui.displaySelection.filter.DisplaySelectionItemsFilter;
import delta.games.lotro.maps.ui.displaySelection.table.DisplaySelectionTableController;

/**
 * Controller for a panel with show/hide buttons for display selection.
 * @author DAM
 */
public class DisplaySelectionVisibilityEditionPanelController extends AbstractPanelController
{
  // Controllers
  private DisplaySelectionTableController _tableController;
  // Data
  private DisplaySelection _displaySelection;
  private DisplaySelectionItemsFilter _itemsFilter;
  // Listeners
  private FilterUpdateListener _visibilityListener;

  /**
   * Constructor.
   * @param tableController Associated table.
   * @param displaySelection Display selection.
   * @param itemsFilter Items filter.
   * @param visibilityListener Visibility listener.
   */
  public DisplaySelectionVisibilityEditionPanelController(DisplaySelectionTableController tableController,
      DisplaySelection displaySelection, DisplaySelectionItemsFilter itemsFilter,
      FilterUpdateListener visibilityListener)
  {
    super();
    _tableController=tableController;
    _displaySelection=displaySelection;
    _itemsFilter=itemsFilter;
    _visibilityListener=visibilityListener;
    JPanel panel=buildPanel();
    setPanel(panel);
  }

  private JPanel buildPanel()
  {
    JPanel ret=GuiFactory.buildPanel(new GridBagLayout());
    buildButtonsRow(ret,"Show:",0,true);
    buildButtonsRow(ret,"Hide:",1,false);
    return ret;
  }

  private void buildButtonsRow(JPanel panel, String header, int row, boolean visible)
  {
    int top=(row==1)?2:0;
    int bottom=(row==0)?2:0;
    int left=5;
    GridBagConstraints c=new GridBagConstraints(0,row,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(top,left,bottom,0),0,0);
    // Header
    JLabel headerLabel=GuiFactory.buildLabel(header);
    panel.add(headerLabel,c);
    c.gridx++;
    // Selected
    JButton selected=buildUpdateSelectionButton(visible);
    panel.add(selected,c);
    c.gridx++;
    // Filtered
    JButton filtered=buildUpdateFilteredButton(visible);
    panel.add(filtered,c);
    c.gridx++;
    // All
    JButton all=buildUpdateAllButton(visible);
    c=new GridBagConstraints(c.gridx,row,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(top,left,bottom,5),0,0);
    panel.add(all,c);
    c.gridx++;
  }

  private JButton buildUpdateSelectionButton(boolean visible)
  {
    JButton ret=GuiFactory.buildButton("Selected");
    ActionListener al=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        updateSelection(visible);
      }
    };
    ret.addActionListener(al);
    return ret;
  }

  private JButton buildUpdateAllButton(boolean visible)
  {
    JButton ret=GuiFactory.buildButton("All");
    ActionListener al=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        updateAll(visible);
      }
    };
    ret.addActionListener(al);
    return ret;
  }

  private JButton buildUpdateFilteredButton(boolean visible)
  {
    JButton ret=GuiFactory.buildButton("Filtered");
    ActionListener al=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        updateFiltered(visible,_itemsFilter);
      }
    };
    ret.addActionListener(al);
    return ret;
  }

  private void updateSelection(boolean visible)
  {
    SelectionManager<DisplaySelectionItem> selectionMgr=_tableController.getTableController().getSelectionManager();
    List<DisplaySelectionItem> selection=selectionMgr.getSelection();
    if (!selection.isEmpty())
    {
      updateItems(selection,visible,_itemsFilter);
    }
  }

  private void updateAll(boolean visible)
  {
    updateFiltered(visible,null);
  }

  private void updateFiltered(boolean visible, DisplaySelectionItemsFilter filter)
  {
    List<DisplaySelectionItem> items=_displaySelection.getItems();
    updateItems(items,visible,filter);
  }

  private void updateItems(List<DisplaySelectionItem> items, boolean visible, DisplaySelectionItemsFilter filter)
  {
    boolean changed=false;
    List<DisplaySelectionItem> changedItems=new ArrayList<DisplaySelectionItem>();
    for(DisplaySelectionItem item : items)
    {
      if ((filter==null) || (filter.accept(item)))
      {
        boolean itemIsVisible=item.isVisible();
        if (itemIsVisible!=visible)
        {
          item.setVisible(visible);
          changedItems.add(item);
          changed=true;
        }
      }
    }
    if (changed)
    {
      // Update map
      _visibilityListener.filterUpdated();
      // Update table
      GenericTableController<DisplaySelectionItem> tableCtrl=_tableController.getTableController();
      for(DisplaySelectionItem item : changedItems)
      {
        tableCtrl.refresh(item);
      }
    }
  }

  @Override
  public void dispose()
  {
    super.dispose();
    // Controllers
    _tableController=null;
    // Data
    _displaySelection=null;
    _itemsFilter=null;
    // Listeners
    _visibilityListener=null;
  }
}
