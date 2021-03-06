package gui;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.text.*;

/* This work is hereby released into the Public Domain.
 * To view a copy of the public domain dedication, visit
 * http://creativecommons.org/licenses/publicdomain/
 */
public class AutoCompletion extends PlainDocument {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2013898825421502284L;
	private JComboBox<String> comboBox;
	private ComboBoxModel<String> model;
	private JTextComponent editor;
// flag to indicate if setSelectedItem has been called
// subsequent calls to remove/insertString should be ignored
	private boolean selecting = false;
	private boolean hidePopupOnFocusLoss;
	private boolean hitBackspace = false;
	private boolean hitBackspaceOnSelection;

	private KeyListener editorKeyListener;
	private FocusListener editorFocusListener;
	private void putComboBox() {
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!selecting)
					highlightCompletedText(0);
			}
		});
		comboBox.addPropertyChangeListener(new PropertyChangeListener() {
			@SuppressWarnings("unchecked")
			public void propertyChange(PropertyChangeEvent e) {
				if (e.getPropertyName().equals("editor"))
					configureEditor((ComboBoxEditor) e.getNewValue());
				if (e.getPropertyName().equals("model"))
					model = (ComboBoxModel<String>) e.getNewValue();
			}
		});
	}
	private void putEditor() {
		editorKeyListener = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (comboBox.isDisplayable())
					comboBox.setPopupVisible(true);
				hitBackspace = false;
				int x = e.getKeyCode();
				switch (x) {
				// determine if the pressed key is backspace (needed by the remove method)
				case KeyEvent.VK_BACK_SPACE:
					hitBackspace = true;
					hitBackspaceOnSelection = editor.getSelectionStart() != editor.getSelectionEnd();
					break;
				// ignore delete key
				case KeyEvent.VK_DELETE:
					e.consume();
					comboBox.getToolkit().beep();
					break;
				default:
					break;
				}
			}
		};
	}
	public AutoCompletion(final JComboBox<String> comboBox) {
		this.comboBox = comboBox;
		model = comboBox.getModel();
		putComboBox();
		putEditor();
		// Bug 5100422 on Java 1.5: Editable JComboBox won't hide popup when tabbing out
		hidePopupOnFocusLoss = System.getProperty("java.version").startsWith("1.5");
		// Highlight whole text when gaining focus
		editorFocusListener = new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				highlightCompletedText(0);
			}

			public void focusLost(FocusEvent e) {
				// Workaround for Bug 5100422 - Hide Popup on focus loss
				if (hidePopupOnFocusLoss)
					comboBox.setPopupVisible(false);
			}
		};
		configureEditor(comboBox.getEditor());
		// Handle initially selected object
		Object selected = comboBox.getSelectedItem();
		if (selected != null) {
			try {
				setText(selected.toString());
			} catch (BadLocationException e1) {
			}
		}
		highlightCompletedText(0);
	}

	public static void enable(JComboBox<String> comboBox) {
		// has to be editable
		comboBox.setEditable(true);
		// change the editor's document
		new AutoCompletion(comboBox);
	}

	public void configureEditor(ComboBoxEditor newEditor) {
		if (editor != null) {
			editor.removeKeyListener(editorKeyListener);
			editor.removeFocusListener(editorFocusListener);
		}

		if (newEditor != null) {
			editor = (JTextComponent) newEditor.getEditorComponent();
			editor.addKeyListener(editorKeyListener);
			editor.addFocusListener(editorFocusListener);
			editor.setDocument(this);
		}
	}

	public void remove(int offs, int len) throws BadLocationException {
		// return immediately when selecting an item
		int x = offs;
		if (selecting)
			return;
		if (hitBackspace) {
			// user hit backspace => move the selection backwards
			// old item keeps being selected
			if (x > 0) {
				if (hitBackspaceOnSelection)
					x--;
			} else {
				// User hit backspace with the cursor positioned on the start => beep
				comboBox.getToolkit().beep(); // when available use:
			}
			highlightCompletedText(x);
		} else {
			super.remove(x, len);
		}
	}
	
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		// return immediately when selecting an item
		if (selecting)
			return;
		// insert the string into the document
		int x= offs;
		super.insertString(x, str, a);
		// lookup and select a matching item
		Object item = lookupItem(getText(0, getLength()));
		if (item != null) {
			setSelectedItem(item);
		} else {
			// keep old item selected if there is no match
			item = comboBox.getSelectedItem();
			// imitate no insert (later on offs will be incremented by str.length():
			// selection won't move forward)
			x = x - str.length();
			// provide feedback to the user that his input has been received but can not be
			// accepted
			comboBox.getToolkit().beep(); // when available use:
		}
		if (item != null) {
			setText(item.toString());
		}
		// select the completed part
		highlightCompletedText(x + str.length());
	}

	private void setText(String text) throws BadLocationException {
		try {
			// remove all text and insert the completed string
			super.remove(0, getLength());
			super.insertString(0, text, null);
		} catch (BadLocationException e) {
			throw new BadLocationException(e.toString(), 1);
		}
	}

	private void highlightCompletedText(int start) {
		editor.setCaretPosition(getLength());
		editor.moveCaretPosition(start);
	}

	private void setSelectedItem(Object item) {
		selecting = true;
		model.setSelectedItem(item);
		selecting = false;
	}

	private Object lookupItem(String pattern) {
		Object selectedItem = model.getSelectedItem();
		// only search for a different item if the currently selected does not match
		if (selectedItem != null && startsWithIgnoreCase(selectedItem.toString(), pattern)) {
			return selectedItem;
		} else {
			// iterate over all items
			for (int i = 0, n = model.getSize(); i < n; i++) {
				Object currentItem = model.getElementAt(i);
				// current item starts with the pattern?
				if (currentItem != null && startsWithIgnoreCase(currentItem.toString(), pattern)) {
					return currentItem;
				}
			}
		}
		// no item starts with the pattern => return null
		return null;
	}

// checks if str1 starts with str2 - ignores case
	private boolean startsWithIgnoreCase(String str1, String str2) {
		return str1.toUpperCase().startsWith(str2.toUpperCase());
	}

}