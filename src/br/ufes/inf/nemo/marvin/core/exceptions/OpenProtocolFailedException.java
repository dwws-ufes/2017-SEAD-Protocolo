package br.ufes.inf.nemo.marvin.core.exceptions;

/**
 * Application exception that represents the fact that the system installation process has failed to complete.
 * 
 * @author Wagner de A. Perin (wagnerperin@gmail.com)
 */
public class OpenProtocolFailedException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Constructor from superclass. */
	public OpenProtocolFailedException(Throwable t) {
		super(t);
	}
}
