package br.com.rafaelblomer.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {
	private static final long serialVersionUID = 4755970511964761684L;
	
	private Long timestamp;
    private Integer status;
    private String error, message, path;
}
