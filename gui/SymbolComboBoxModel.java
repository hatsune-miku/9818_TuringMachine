package gui;

import javax.swing.ComboBoxModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import turmach.model.TuringMachine;

public class SymbolComboBoxModel implements ComboBoxModel<Character> {
	protected TuringMachine model ;
	protected Object selectedObject ;
	protected EventListenerList listenerList = new EventListenerList();
	
	
	SymbolComboBoxModel( TuringMachine model ) {
		this.model = model ;
		if( model.getSymbolList().size() > 0 ) {
			selectedObject = model.getSymbolList().get(0) ;
		}
	}

	@Override
	public int getSize() {
		return this.model.getSymbolList().size() ;
	}

	@Override
	public Character getElementAt(int index) {
		if ( index >= 0 && index < getSize() )
            return this.model.getSymbolList().get( index );
        else
            return null;
	}

	@Override
	 public void addListDataListener(ListDataListener l) {
        listenerList.add(ListDataListener.class, l);
    }

	@Override
	public void removeListDataListener(ListDataListener l) {
        listenerList.remove(ListDataListener.class, l);
    }
	
	@Override
	public void setSelectedItem(Object anObject) {
        if ((selectedObject != null && !selectedObject.equals( anObject )) ||
                selectedObject == null && anObject != null) {
                selectedObject = anObject;
                fireContentsChanged(this, -1, -1);
            }
        }

	@Override
	public Object getSelectedItem() {
		return selectedObject;
	}
	
	protected void fireContentsChanged(Object source, int index0, int index1)
    {
        Object[] listeners = listenerList.getListenerList();
        ListDataEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ListDataListener.class) {
                if (e == null) {
                    e = new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, index0, index1);
                }
                ((ListDataListener)listeners[i+1]).contentsChanged(e);
            }
        }
    }

}
