package org.com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.security.PublicKey;

@Getter
@AllArgsConstructor
@Setter
public class Player {

private int id;
private String name;
private  Position position; //Owns position

public  Player(int id,String name){
	this.id=id;
	this.name=name;
	this.position=new Position (0);
	
}



}
