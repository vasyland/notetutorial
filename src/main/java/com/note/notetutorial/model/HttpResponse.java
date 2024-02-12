package com.note.notetutorial.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatusCode;

import java.io.Serializable;
import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@SuperBuilder
@JsonInclude(NON_NULL)
public class HttpResponse<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    protected String timeStamp;
    protected int statusCode;
    protected HttpStatusCode status;
    protected String reason;
    protected String message;
    protected String developerMessage;
    protected Collection<? extends T> notes;
}
