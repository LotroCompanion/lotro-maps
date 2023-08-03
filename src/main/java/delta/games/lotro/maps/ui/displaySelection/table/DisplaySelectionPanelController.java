package delta.games.lotro.maps.ui.displaySelection.table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.tables.panel.FilterUpdateListener;
import delta.common.ui.swing.windows.WindowController;
import delta.common.ui.swing.windows.WindowsManager;

/**
 * Controller the display selection display panel for a category.
 * @author DAM
 */
public class DisplaySelectionPanelController implements FilterUpdateListener
{
  // Data
  private DisplaySelectionTableController _tableController;
  // GUI
  private JPanel _panel;
  private JLabel _statsLabel;
  private WindowsManager _windowsManager;
  // Controllers
  private WindowController _parent;

  /**
   * Constructor.
   * @param parent Parent window.
   * @param tableController Associated table controller.
   */
  public DisplaySelectionPanelController(WindowController parent, DisplaySelectionTableController tableController)
  {
    _parent=parent;
    _tableController=tableController;
    _windowsManager=new WindowsManager();
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    if (_panel==null)
    {
      _panel=build();
      updateStatsLabel();
    }
    return _panel;
  }

  private JPanel build()
  {
    JPanel panel=GuiFactory.buildBackgroundPanel(new BorderLayout());
    TitledBorder frameBorder=GuiFactory.buildTitledBorder("Selection");
    panel.setBorder(frameBorder);

    // Table
    JTable table=_tableController.getTable();
    JScrollPane scroll=GuiFactory.buildScrollPane(table);
    panel.add(scroll,BorderLayout.CENTER);
    // Stats
    JPanel statsPanel=GuiFactory.buildPanel(new FlowLayout(FlowLayout.LEFT));
    _statsLabel=GuiFactory.buildLabel("-");
    statsPanel.add(_statsLabel);
    panel.add(statsPanel,BorderLayout.NORTH);
    return panel;
  }

  @Override
  public void filterUpdated()
  {
    _tableController.updateFilter();
    updateStatsLabel();
  }

  private void updateStatsLabel()
  {
    int nbFiltered=_tableController.getNbFilteredItems();
    int nbItems=_tableController.getNbItems();
    String label="";
    if (nbFiltered==nbItems)
    {
      label="Element(s): "+nbItems;
    }
    else
    {
      label="Element(s): "+nbFiltered+"/"+nbItems;
    }
    _statsLabel.setText(label);
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _tableController=null;
    // GUI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    _statsLabel=null;
    if (_windowsManager!=null)
    {
      _windowsManager.disposeAll();
      _windowsManager=null;
    }
    // Controllers
    _parent=null;
  }
}
