package com.zhiyin.android.im.model;

import com.zhiyin.android.im.model.Entity.Builder;

public class TestEntity extends Entity implements Builder{

	private static final long serialVersionUID = -9017376980470243998L;
	private String id;  
    private String name;
    private int age = 100;

    public String getId() {  
        return id;  
    }  

    public void setId(String id) {  
        this.id = id;  
    }  

    public String getName() {  
        return name;  
    }  

    public void setName(String name) {  
        this.name = name;  
    }

    public int getAge() {  
        return age;  
    }  

    public void setAge(int age) {
        this.age = age;
    }
}
