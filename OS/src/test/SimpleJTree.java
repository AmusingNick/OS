package test;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class SimpleJTree {
	JFrame jf = new JFrame();
	JTree tree;
	DefaultMutableTreeNode root ;
	DefaultMutableTreeNode guangdong ;
	DefaultMutableTreeNode guangxi ;
	DefaultMutableTreeNode foshan ;
	DefaultMutableTreeNode shantou ;
	DefaultMutableTreeNode guilin ;
	DefaultMutableTreeNode nanning;
	public void init(){
		root = new DefaultMutableTreeNode("�й�");
		guangdong = new DefaultMutableTreeNode("�㶫");
		guangxi = new DefaultMutableTreeNode("����");
		foshan = new DefaultMutableTreeNode("��ɽ");
		shantou = new DefaultMutableTreeNode("��ͷ");
		guilin= new DefaultMutableTreeNode("����");
		nanning = new DefaultMutableTreeNode("����");
		guangdong.add(foshan);
		guangdong.add(shantou);
		guangxi.add(guilin);
		guangxi.add(nanning);
		root.add(guangxi);
		root.add(guangdong);
		
		tree = new JTree(root);
		jf.add(new JScrollPane(tree));
		jf.pack();
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//tree.putClientProperty("JTree.lineStyle", "None");
		//tree.putClientProperty("JTree.lineStyle", "Horizontal");
	}
	
	
	public static void main(String[] args) {
		new SimpleJTree().init();
	}
}
