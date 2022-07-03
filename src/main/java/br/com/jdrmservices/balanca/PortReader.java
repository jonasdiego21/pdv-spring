package br.com.jdrmservices.balanca;

import java.util.logging.Logger;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class PortReader implements SerialPortEventListener {
	
	private final Logger LOG = Logger.getLogger(Balanca.class.getName());
	private SerialPort serialPort;
	private String peso;

    public PortReader(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR() && event.getEventValue() > 0) {
            try {
            	this.setPeso(serialPort.readString(event.getEventValue()).replaceAll("\\W+", ""));
            	System.out.println("=> valor da balanca: " + this.getPeso());
            } catch (SerialPortException ex) {
                LOG.severe(ex.getPortName() + " - " + ex.getMessage());
            }
        }
    }
    
    public String getPeso() {
		return this.peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}
}