package com.weakie.desktop.view;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;
import swing2swt.layout.BoxLayout;

import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;
import com.weakie.share.jni.SendData;
import com.weakie.share.util.LogUtil;

public class MainView {

	protected Shell shell;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Composite composite;
	private Spinner spinner_x;
	private Spinner spinner_y;
	private Spinner spinner_z;
	private Spinner spinner_dx;
	private Spinner spinner_dy;
	private Spinner spinner_dz;
	private Spinner spinner_sx;
	private Spinner spinner_sy;
	private Spinner spinner_sz;
	private Label comStateLabel;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setMinimumSize(new Point(822, 600));
		shell.setModified(true);
		shell.setSize(987, 624);
		shell.setText("COM Debug Tool");
		shell.setLayout(new BorderLayout(0, 0));
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.V_SCROLL);
		scrolledComposite.setLayoutData(BorderLayout.WEST);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		composite = new Composite(scrolledComposite, SWT.BORDER);
		RowLayout rl_composite = new RowLayout(SWT.VERTICAL);
		rl_composite.fill = true;
		rl_composite.center = true;
		rl_composite.wrap = false;
		composite.setLayout(rl_composite);
		
		{
			ExpandableComposite xpndblcmpstcom = formToolkit.createExpandableComposite(composite, ExpandableComposite.TWISTIE);
			xpndblcmpstcom.setBackground(SWTResourceManager.getColor(211, 211, 211));
			xpndblcmpstcom.addExpansionListener(new ExpansionAdapter() {
				public void expansionStateChanged(ExpansionEvent arg0) {
					composite.layout();
					scrolledComposite.layout();
					shell.layout();
				}
			});
			formToolkit.paintBordersFor(xpndblcmpstcom);
			xpndblcmpstcom.setText("\u914D\u7F6E\u7AEF\u53E3\u53C2\u6570");
			
			Group group = new Group(xpndblcmpstcom, SWT.NONE);
			xpndblcmpstcom.setClient(group);
			group.setFont(SWTResourceManager.getFont("\u5FAE\u8F6F\u96C5\u9ED1", 9, SWT.NORMAL));
			group.setText("\u521D\u59CB\u5316\u53C2\u6570");
			RowLayout rl_group = new RowLayout(SWT.VERTICAL);
			rl_group.marginLeft = 5;
			rl_group.wrap = false;
			rl_group.spacing = 1;
			rl_group.marginWidth = 3;
			rl_group.marginTop = 5;
			rl_group.marginRight = 2;
			rl_group.marginBottom = 5;
			rl_group.justify = true;
			rl_group.center = true;
			rl_group.fill = true;
			group.setLayout(rl_group);
			
			Composite composite_4 = new Composite(group, SWT.NONE);
			GridLayout gl_composite_4 = new GridLayout(2, false);
			gl_composite_4.marginHeight = 2;
			composite_4.setLayout(gl_composite_4);
			
			Label label = new Label(composite_4, SWT.NONE);
			label.setText("\u7AEF\u53E3\u53F7");
			
			Combo combo = new Combo(composite_4, SWT.NONE);
			combo.setItems(new String[] {"1", "2", "3", "4"});
			combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			formToolkit.adapt(combo);
			formToolkit.paintBordersFor(combo);
			combo.select(0);
			
			Composite composite_5 = new Composite(group, SWT.NONE);
			GridLayout gl_composite_5 = new GridLayout(2, false);
			gl_composite_5.marginHeight = 2;
			composite_5.setLayout(gl_composite_5);
			
			Label label_1 = new Label(composite_5, SWT.NONE);
			label_1.setText("\u6CE2\u7279\u7387");
			
			Combo combo_1 = new Combo(composite_5, SWT.NONE);
			combo_1.setItems(new String[] {"1", "2", "3", "4"});
			combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			Composite composite_6 = new Composite(group, SWT.NONE);
			GridLayout gl_composite_6 = new GridLayout(2, false);
			gl_composite_6.marginHeight = 2;
			composite_6.setLayout(gl_composite_6);
			
			Label label_2 = new Label(composite_6, SWT.NONE);
			label_2.setText("\u6570\u636E\u4F4D");
			
			Combo combo_2 = new Combo(composite_6, SWT.NONE);
			combo_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			Composite composite_7 = new Composite(group, SWT.NONE);
			GridLayout gl_composite_7 = new GridLayout(2, false);
			gl_composite_7.marginHeight = 2;
			composite_7.setLayout(gl_composite_7);
			
			Label label_3 = new Label(composite_7, SWT.NONE);
			label_3.setText("\u505C\u6B62\u4F4D");
			
			Combo combo_3 = new Combo(composite_7, SWT.NONE);
			combo_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			Composite composite_8 = new Composite(group, SWT.NONE);
			GridLayout gl_composite_8 = new GridLayout(2, false);
			gl_composite_8.marginHeight = 2;
			composite_8.setLayout(gl_composite_8);
			
			Label label_4 = new Label(composite_8, SWT.NONE);
			label_4.setText("\u68C0\u9A8C\u4F4D");
			
			Combo combo_4 = new Combo(composite_8, SWT.NONE);
			combo_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
		}
		{

			ExpandableComposite expandableComposite_2 = formToolkit.createExpandableComposite(composite, ExpandableComposite.TWISTIE);
			expandableComposite_2.addExpansionListener(new ExpansionAdapter() {
				public void expansionStateChanged(ExpansionEvent arg0) {
					composite.layout();
					scrolledComposite.layout();
					shell.layout();
				}
			});
			expandableComposite_2.setBackground(SWTResourceManager.getColor(211, 211, 211));
			formToolkit.paintBordersFor(expandableComposite_2);
			expandableComposite_2.setText("\u8BBE\u7F6E\u79FB\u52A8\u901F\u5EA6");
			expandableComposite_2.setExpanded(true);
			
			Group group_2 = new Group(expandableComposite_2, SWT.NONE);
			expandableComposite_2.setClient(group_2);
			group_2.setText("\u79FB\u52A8\u901F\u5EA6");
			RowLayout rl_group_2 = new RowLayout(SWT.VERTICAL);
			rl_group_2.wrap = false;
			rl_group_2.spacing = 1;
			rl_group_2.marginWidth = 3;
			rl_group_2.marginTop = 5;
			rl_group_2.marginRight = 2;
			rl_group_2.marginLeft = 5;
			rl_group_2.marginBottom = 5;
			rl_group_2.center = true;
			rl_group_2.justify = true;
			rl_group_2.fill = true;
			group_2.setLayout(rl_group_2);
			
			Composite composite_1 = new Composite(group_2, SWT.NONE);
			composite_1.setLayoutData(new RowData(139, SWT.DEFAULT));
			GridLayout gl_composite_1 = new GridLayout(2, false);
			gl_composite_1.marginHeight = 2;
			composite_1.setLayout(gl_composite_1);
			
			Label label = new Label(composite_1, SWT.NONE);
			label.setText("X\u901F\u5EA6");
			
			spinner_sx = new Spinner(composite_1, SWT.BORDER);
			spinner_sx.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			spinner_sx.setMaximum(10000000);
			spinner_sx.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
			formToolkit.adapt(spinner_sx);
			formToolkit.paintBordersFor(spinner_sx);
			
			Composite composite_2 = new Composite(group_2, SWT.NONE);
			composite_2.setLayoutData(new RowData(139, SWT.DEFAULT));
			GridLayout gl_composite_2 = new GridLayout(2, false);
			gl_composite_2.marginHeight = 2;
			composite_2.setLayout(gl_composite_2);
			
			Label lblY_1 = new Label(composite_2, SWT.NONE);
			lblY_1.setText("Y\u901F\u5EA6");
			
			spinner_sy = new Spinner(composite_2, SWT.BORDER);
			spinner_sy.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			spinner_sy.setMaximum(10000000);
			spinner_sy.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
			formToolkit.adapt(spinner_sy);
			formToolkit.paintBordersFor(spinner_sy);
			
			Composite composite_3 = new Composite(group_2, SWT.NONE);
			composite_3.setLayoutData(new RowData(139, SWT.DEFAULT));
			GridLayout gl_composite_3 = new GridLayout(2, false);
			gl_composite_3.marginHeight = 2;
			composite_3.setLayout(gl_composite_3);
			
			Label lblZ_1 = new Label(composite_3, SWT.NONE);
			lblZ_1.setText("Z\u901F\u5EA6");
			
			spinner_sz = new Spinner(composite_3, SWT.BORDER);
			spinner_sz.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			spinner_sz.setMaximum(10000000);
			spinner_sz.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
			formToolkit.adapt(spinner_sz);
			formToolkit.paintBordersFor(spinner_sz);
			
		}
		{
			ExpandableComposite expandableComposite_1 = formToolkit.createExpandableComposite(composite, ExpandableComposite.TWISTIE);
			expandableComposite_1.addExpansionListener(new ExpansionAdapter() {
				public void expansionStateChanged(ExpansionEvent arg0) {
					composite.layout();
					scrolledComposite.layout();
					shell.layout();
				}
			});
			expandableComposite_1.setBackground(SWTResourceManager.getColor(211, 211, 211));
			formToolkit.paintBordersFor(expandableComposite_1);
			expandableComposite_1.setText("\u8BBE\u7F6E\u76EE\u6807\u4F4D\u7F6E");
			expandableComposite_1.setExpanded(true);
			
			Group group_1 = new Group(expandableComposite_1, SWT.NONE);
			expandableComposite_1.setClient(group_1);
			group_1.setText("\u76EE\u6807\u4F4D\u7F6E");
			RowLayout rl_group_1 = new RowLayout(SWT.VERTICAL);
			rl_group_1.wrap = false;
			rl_group_1.spacing = 1;
			rl_group_1.marginWidth = 3;
			rl_group_1.marginTop = 5;
			rl_group_1.marginRight = 2;
			rl_group_1.marginLeft = 5;
			rl_group_1.marginBottom = 5;
			rl_group_1.justify = true;
			rl_group_1.center = true;
			rl_group_1.fill = true;
			group_1.setLayout(rl_group_1);
			
			Composite composite_2_1 = new Composite(group_1, SWT.NONE);
			composite_2_1.setLayoutData(new RowData(139, SWT.DEFAULT));
			GridLayout gl_composite_2_1 = new GridLayout(2, false);
			gl_composite_2_1.marginHeight = 2;
			composite_2_1.setLayout(gl_composite_2_1);
			
			Label lblX = new Label(composite_2_1, SWT.NONE);
			lblX.setText("X\u5750\u6807");
			
			spinner_x = new Spinner(composite_2_1, SWT.BORDER);
			spinner_x.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
			spinner_x.setMaximum(10000000);
			spinner_x.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			formToolkit.adapt(spinner_x);
			formToolkit.paintBordersFor(spinner_x);
			
			Composite composite_3 = new Composite(group_1, SWT.NONE);
			composite_3.setLayoutData(new RowData(139, SWT.DEFAULT));
			GridLayout gl_composite_3 = new GridLayout(2, false);
			gl_composite_3.marginHeight = 2;
			composite_3.setLayout(gl_composite_3);
			
			Label lblY = new Label(composite_3, SWT.NONE);
			lblY.setText("Y\u5750\u6807");
			
			spinner_y = new Spinner(composite_3, SWT.BORDER);
			spinner_y.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
			spinner_y.setMaximum(10000000);
			spinner_y.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			formToolkit.adapt(spinner_y);
			formToolkit.paintBordersFor(spinner_y);
			
			Composite composite_12 = new Composite(group_1, SWT.NONE);
			composite_12.setLayoutData(new RowData(139, SWT.DEFAULT));
			GridLayout gl_composite_12 = new GridLayout(2, false);
			gl_composite_12.marginHeight = 2;
			composite_12.setLayout(gl_composite_12);
			
			Label lblZ = new Label(composite_12, SWT.NONE);
			lblZ.setText("Z\u5750\u6807");
			
			spinner_z = new Spinner(composite_12, SWT.BORDER);
			spinner_z.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
			spinner_z.setMaximum(10000000);
			spinner_z.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			formToolkit.adapt(spinner_z);
			formToolkit.paintBordersFor(spinner_z);
		}
		{
			ExpandableComposite expandableComposite = formToolkit.createExpandableComposite(composite, ExpandableComposite.TWISTIE);
			expandableComposite.addExpansionListener(new ExpansionAdapter() {
				public void expansionStateChanged(ExpansionEvent arg0) {
					composite.layout();
					scrolledComposite.layout();
					shell.layout();
				}
			});
			expandableComposite.setBackground(SWTResourceManager.getColor(211, 211, 211));
			formToolkit.paintBordersFor(expandableComposite);
			expandableComposite.setText("\u8BBE\u7F6E\u76F8\u5BF9\u79FB\u52A8");
			expandableComposite.setExpanded(true);
			
			Group group_3 = new Group(expandableComposite, SWT.NONE);
			expandableComposite.setClient(group_3);
			group_3.setText("\u79FB\u52A8\u8DDD\u79BB");
			RowLayout rl_group_3 = new RowLayout(SWT.VERTICAL);
			rl_group_3.justify = true;
			rl_group_3.wrap = false;
			rl_group_3.spacing = 1;
			rl_group_3.marginWidth = 3;
			rl_group_3.marginTop = 5;
			rl_group_3.marginRight = 2;
			rl_group_3.marginLeft = 5;
			rl_group_3.marginBottom = 5;
			rl_group_3.fill = true;
			rl_group_3.center = true;
			group_3.setLayout(rl_group_3);
			
			Composite composite_16 = new Composite(group_3, SWT.NONE);
			GridLayout gl_composite_16 = new GridLayout(2, false);
			gl_composite_16.marginHeight = 2;
			composite_16.setLayout(gl_composite_16);
			
			spinner_dx = new Spinner(composite_16, SWT.BORDER);
			spinner_dx.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			spinner_dx.setMaximum(10000000);
			spinner_dx.setMinimum(-10000000);
			spinner_dx.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
			formToolkit.adapt(spinner_dx);
			formToolkit.paintBordersFor(spinner_dx);
			
			Button btnX = new Button(composite_16, SWT.NONE);
			btnX.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int x = MainView.this.spinner_x.getSelection();
					int dx = MainView.this.spinner_dx.getSelection();
					MainView.this.spinner_x.setSelection(x+dx);
					LogUtil.info("Relatively move in x direction: x="+x+", dx="+dx);
					MainView.this.moveToPoint();
				}
			});
			btnX.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
			formToolkit.adapt(btnX, true, true);
			btnX.setText("X\u79FB\u52A8");
			
			Composite composite_17 = new Composite(group_3, SWT.NONE);
			GridLayout gl_composite_17 = new GridLayout(2, false);
			gl_composite_17.marginHeight = 2;
			composite_17.setLayout(gl_composite_17);
			
			spinner_dy = new Spinner(composite_17, SWT.BORDER);
			spinner_dy.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			spinner_dy.setMaximum(10000000);
			spinner_dy.setMinimum(-10000000);
			spinner_dy.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
			formToolkit.adapt(spinner_dy);
			formToolkit.paintBordersFor(spinner_dy);
			
			Button btnY = new Button(composite_17, SWT.NONE);
			btnY.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int y = MainView.this.spinner_y.getSelection();
					int dy = MainView.this.spinner_dy.getSelection();
					MainView.this.spinner_y.setSelection(y+dy);
					LogUtil.info("Relatively move in y direction: y="+y+", dy="+dy);
					MainView.this.moveToPoint();
				}
			});
			formToolkit.adapt(btnY, true, true);
			btnY.setText("Y\u79FB\u52A8");
			
			Composite composite_18 = new Composite(group_3, SWT.NONE);
			GridLayout gl_composite_18 = new GridLayout(2, false);
			gl_composite_18.marginHeight = 2;
			composite_18.setLayout(gl_composite_18);
			
			spinner_dz = new Spinner(composite_18, SWT.BORDER);
			spinner_dz.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			spinner_dz.setMaximum(10000000);
			spinner_dz.setMinimum(-10000000);
			spinner_dz.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
			formToolkit.adapt(spinner_dz);
			formToolkit.paintBordersFor(spinner_dz);
			
			Button btnZ = new Button(composite_18, SWT.NONE);
			btnZ.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int z = MainView.this.spinner_z.getSelection();
					int dz = MainView.this.spinner_dz.getSelection();
					MainView.this.spinner_z.setSelection(z+dz);
					LogUtil.info("Relatively move in z direction: z="+z+", dz="+dz);
					MainView.this.moveToPoint();
				}
			});
			formToolkit.adapt(btnZ, true, true);
			btnZ.setText("Z\u79FB\u52A8");
		}
		
		Composite composite_11 = new Composite(composite, SWT.NONE);
		composite_11.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		RowLayout rl_composite_11 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_11.center = true;
		rl_composite_11.fill = true;
		rl_composite_11.wrap = false;
		composite_11.setLayout(rl_composite_11);
		
		Button button_1 = new Button(composite_11, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SendData.getInstance().initCOM();
				if(SendData.getInstance().isInited()){
					comStateLabel.setText("已连接");
					comStateLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
				} else{
					comStateLabel.setText("未连接");
					comStateLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
				}
			}
		});
		formToolkit.adapt(button_1, true, true);
		button_1.setText("\u8FDE\u63A5\u7AEF\u53E3");
		
		Composite composite_2 = new Composite(composite_11, SWT.NONE);
		composite_2.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		comStateLabel = new Label(composite_2, SWT.SHADOW_NONE);
		comStateLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		comStateLabel.setText("\u672A\u8FDE\u63A5");
		
		Composite composite_14 = new Composite(composite, SWT.NONE);
		RowLayout rl_composite_14 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_14.fill = true;
		rl_composite_14.center = true;
		composite_14.setLayout(rl_composite_14);
		Button button_2 = new Button(composite_14, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MainView.this.moveToPoint();
			}
		});
		button_2.setText("\u79FB\u52A8\u4F4D\u7F6E");
		
		Button button = new Button(composite_14, SWT.NONE);
		formToolkit.adapt(button, true, true);
		button.setText("\u7D27\u6025\u590D\u4F4D");
		button.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				MainView.this.moveToOriginPoint();
			}
		});
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(new Point(80, 677));
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new BorderLayout(0, 0));
		
		CTabFolder tabFolder = new CTabFolder(composite_1, SWT.BORDER | SWT.CLOSE | SWT.FLAT);
		tabFolder.setMinimized(true);
		tabFolder.setMinimizeVisible(true);
		tabFolder.setMaximized(true);
		tabFolder.setMaximizeVisible(true);
		tabFolder.setLayoutData(BorderLayout.CENTER);
		formToolkit.adapt(tabFolder);
		formToolkit.paintBordersFor(tabFolder);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("New Item");
		
		Composite composite_15 = new TabEditView(tabFolder, SWT.NONE);
		tabItem.setControl(composite_15);
		formToolkit.paintBordersFor(composite_15);
		
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("\u6587\u4EF6");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

	}
	
	private void moveToPoint(){
		byte[] buf = new byte[32];
		try{
			int x = this.spinner_x.getSelection();
			int y = this.spinner_y.getSelection();
			int z = this.spinner_z.getSelection();
			int sx = this.spinner_sx.getSelection();
			int sy = this.spinner_sy.getSelection();
			int sz = this.spinner_sz.getSelection();
			LogUtil.info("Move to Point:("+x+","+y+","+z+"), Speed:("+sx+","+sy+","+sz+")");
			//SendData.getInstance().formateData(new Point3D(x, y, z), new Speed(sx, sy, sz), buf);
			//SendData.getInstance().sendData(buf);
			LogUtil.info(this.generateFormatedData(buf));
		}catch(Exception e2){
			LogUtil.error(e2);
		}
	}
	
	private void moveToOriginPoint(){
		byte[] buf = new byte[32];
		try{
			LogUtil.info("Move to Origin Point:(0,0,0), Speed:(20000,20000,20000)");
			SendData.getInstance().formateData(new Point3D(0, 0, 0), new Speed(20000, 20000, 20000), buf);
			SendData.getInstance().sendData(buf);
			LogUtil.info(this.generateFormatedData(buf));
		}catch(Exception e2){
			LogUtil.error(e2);
		}
	}
	
	private String generateFormatedData(byte[] buf){
		StringBuilder sb = new StringBuilder();
		sb.append("Formated data to send:[");
		for(byte b:buf){
			sb.append(b+" ");
		}
		sb.append("]");
		return sb.toString();
	}
}
