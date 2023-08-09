package delta.games.lotro.maps.ui.filter;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.panels.AbstractPanelController;

/**
 * Controller for a popup button that triggers a panel.
 * @author DAM
 */
public class PopupButtonController
{
  // UI
  private JPanel _panel;
  private JButton _triggerButton;
  private Popup _popup;
  // Controllers
  private AbstractPanelController _popupPanelController;

  /**
   * Constructor.
   * @param popupPanelController Controller for the managed panel.
   */
  public PopupButtonController(AbstractPanelController popupPanelController)
  {
    _popupPanelController=popupPanelController;
    build();
  }

  /**
   * Get the button used to trigger the popup panel.
   * @return A button.
   */
  public JButton getTriggerButton()
  {
    return _triggerButton;
  }

  private void build()
  {
    _panel=buildPanel();
    _triggerButton=GuiFactory.buildButton("...");
    ActionListener al=new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        if (_popup!=null)
        {
          hide();
        }
        else
        {
          show();
        }
      }
    };
    _triggerButton.addActionListener(al);
  }

  /**
   * Define a button as a button that will close the managed popup.
   * @param closeButton Button to customize.
   */
  public void addCloseButton(JButton closeButton)
  {
    ActionListener alClose=new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        if (_popup!=null)
        {
          hide();
        }
      }
    };
    closeButton.addActionListener(alClose);
  }

  private void show()
  {
    PopupFactory popupFactory=PopupFactory.getSharedInstance();
    Point p=_triggerButton.getLocationOnScreen();
    int x=p.x;
    int y=p.y+_triggerButton.getHeight();
    _popup=popupFactory.getPopup(_triggerButton,_panel,x,y);
    _popup.show();
  }

  private void hide()
  {
    if (_popup!=null)
    {
      _popup.hide();
      _popup=null;
    }
  }

  private JPanel buildPanel()
  {
    JPanel filterPanel=_popupPanelController.getPanel();
    Border emptyBorder=BorderFactory.createEmptyBorder(5,5,5,5);
    Border blackBorder=BorderFactory.createLineBorder(Color.black,2);
    Border border=BorderFactory.createCompoundBorder(emptyBorder,blackBorder);
    filterPanel.setBorder(border);
    return filterPanel;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // UI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    _triggerButton=null;
    _popup=null;
    // Controllers
    if (_popupPanelController!=null)
    {
      _popupPanelController.dispose();
      _popupPanelController=null;
    }
  }
}
