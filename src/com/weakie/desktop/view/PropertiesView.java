package com.weakie.desktop.view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import swing2swt.layout.BorderLayout;

import com.weakie.desktop.bean.propertie.ViewProperties;
import com.weakie.desktop.view.listener.DataModifyListener;
import com.weakie.desktop.view.util.DataFormat;

public class PropertiesView extends Composite {

	private Table table;
	private DataModifyListener listener;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PropertiesView(Composite parent, int style) {
		super(parent, SWT.BORDER | SWT.V_SCROLL);
		setLayout(new BorderLayout(0, 0));
		
		TableViewer tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setLayoutData(BorderLayout.CENTER);
		
		final TableEditor editor = new TableEditor(table);
	    editor.horizontalAlignment = SWT.CENTER;
	    editor.grabHorizontal = true;
	    table.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseDoubleClick(MouseEvent event) {
				//dispose old edit
				Control old = editor.getEditor();
				if (old != null)
					old.dispose();

				//get current table item
				Point pt = new Point(event.x, event.y);
				final TableItem item = table.getItem(pt);
				if (item == null) {
					return;
				}
				//edit in column 1
				final int column = 1;
				Rectangle rect = item.getBounds(column);
				if (!rect.contains(pt)) {
					return;
				}
				//enable cell edit
				final Text text = new Text(table, SWT.NONE);
				text.setText(item.getText(column));
				text.setForeground(item.getForeground());
				text.selectAll();
				text.setFocus();
				
				editor.minimumWidth = text.getBounds().width;
				editor.setEditor(text, item, column);

				text.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						item.setText(column, text.getText());
					}
				});
				
				text.addVerifyListener(new VerifyListener(){   
				    public void verifyText(VerifyEvent e) { 
				    	e.doit = DataFormat.verifyTableData(e.text, column);
				    }   
				});  
				
				text.addFocusListener(new FocusAdapter(){
					@Override
					public void focusLost(FocusEvent arg0) {
						item.setText(column, text.getText());
						text.dispose();
					}
				});

			}
	    });
	    
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnKey = tableViewerColumn.getColumn();
		tblclmnKey.setWidth(100);
		tblclmnKey.setText("key");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnValue = tableViewerColumn_1.getColumn();
		tblclmnValue.setWidth(150);
		tblclmnValue.setText("value");
		
		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(BorderLayout.NORTH);
		
		ToolItem tltmSave = new ToolItem(toolBar, SWT.NONE);
		tltmSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save();
			}
		});
		tltmSave.setText("Save");
	}
	
	public void setPropertiesListener(DataModifyListener listener){
		this.listener = listener;
		this.showProperties();
	}
	
	private void save(){
		if(this.listener == null || !this.listener.isAlive()){
			return;
		}
		this.listener.update(this.getProperties());
	}
	
	private Map<String,String> getProperties(){
		Map<String,String> propertiesMap = new HashMap<String,String>();
		for(TableItem item:this.table.getItems()){
			propertiesMap.put(item.getText(0), item.getText(1));
		}
		return propertiesMap;
	}
	
	private void showProperties(){
		if(this.listener == null || !this.listener.isAlive()){
			return;
		}
		this.table.removeAll();
		ViewProperties p = this.listener.getProperties();
		String[] keys = p.getPropertiesName();
		for(String key:keys){
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(new String[] {key,p.getProperties(key)});
		}
	}
}
