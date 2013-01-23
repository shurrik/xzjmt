package com.xzjmt.common.web;

import java.beans.PropertyEditorSupport;

import org.bson.types.ObjectId;

public class ObjectIdEditor extends PropertyEditorSupport{
	
	public ObjectIdEditor(){
		
	}
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(text==null){
			setValue(null);
		}else{
			setValue(ObjectId.massageToObjectId(text));
		}
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		ObjectId objectId = (ObjectId) getValue();
		return (objectId != null ? objectId.toString():"");
	}

}
