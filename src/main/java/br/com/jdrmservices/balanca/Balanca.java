package br.com.jdrmservices.balanca;

import java.util.logging.Logger;

import jssc.SerialPort;

public class Balanca implements BalancaInterface {
	
	private final Logger LOG = Logger.getLogger(Balanca.class.getName());
	private final String serialPortNumber = "COM2";
	private SerialPort serialPort;
	private PortReader portReader;

	@Override
	public String getPesoBalanca() {
		try {	
			return portReader.getPeso();
		} catch (Exception e) {
			return "0.000";
		}
	}

	@Override
	public boolean conectar() {
        serialPort = new SerialPort(serialPortNumber);
        try {
            LOG.info("Opening port " + serialPortNumber + "...");
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
            serialPort.writeString(serialPortNumber + " opened!");
            
            LOG.info("Port " + serialPortNumber + " opened");
            
            portReader = new PortReader(serialPort);
            serialPort.addEventListener(portReader, SerialPort.MASK_RXCHAR);
            
            return true;
        } catch (Exception e) {
            LOG.severe("There are an error on writing string to port: " + e);
        }

        return false;
	}

	@Override
	public boolean desconectar() {
		SerialPort serialPort = new SerialPort("COM2");
		try {
			serialPort.closePort();
			LOG.info("Port " + serialPortNumber + " closed");
		} catch (Exception e) {
			LOG.severe(e.getMessage());
		}
		
		return false;
	}	
}