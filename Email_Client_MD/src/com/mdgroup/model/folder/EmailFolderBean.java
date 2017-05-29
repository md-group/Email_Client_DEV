package com.mdgroup.model.folder;

import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.mdgroup.model.EmailMessageBean;
import com.mdgroup.view.ViewFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class EmailFolderBean<T> extends TreeItem<String> {
	
	private boolean topElement = false;
	private int unreadMessageCount;
	private String name;
	@SuppressWarnings("unused")
	private String completeName;
	private ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();
	
	/*
	 * Constructor for top elements
	 * @param value
	 */
	
	public EmailFolderBean(String value){
		super(value, ViewFactory.defaultFactory.resolveIcon(value));
		this.name = value;
		this.completeName = value;
		data = null;
		topElement = true;
		this.setExpanded(true);
	}
	
	public EmailFolderBean(String value, String completeName){
		super(value, ViewFactory.defaultFactory.resolveIcon(value));
		this.name = value;
		this.completeName = completeName;
	}
	
	private void updateValue(){
		if(unreadMessageCount > 0){
			this.setValue((String)(name + "(" + unreadMessageCount + ")" ));
		}else {
			this.setValue(name);
		}
	}
	
	public void incrementUnreadMessagesCount(int newMessages){
		unreadMessageCount = unreadMessageCount + newMessages;
		updateValue();
	}
	
	public void decrementUnreadMessagesCount(){
		unreadMessageCount--;
		updateValue();
	}
	
	public void addEmail(int possition, Message message) throws MessagingException{
		boolean isRead = message.getFlags().contains(Flag.SEEN);
		EmailMessageBean emailMessageBean = new EmailMessageBean(message.getSubject(),
				message.getFrom()[0].toString(), 
				message.getSize(), 
				isRead,
				message.getSentDate(),
				message);
		if (possition < 0) {
			data.add(emailMessageBean);
		}
		else{
			data.add(possition, emailMessageBean);
		}
		if(!isRead){
			incrementUnreadMessagesCount(1);
		}
	}
	
	public boolean isTopElement(){
		return topElement;
	}
	
	public ObservableList<EmailMessageBean> getData(){
		return data;
	}
}
