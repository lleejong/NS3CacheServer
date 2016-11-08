package com.choilab.proj.skt.database;

public class NS3InputData {
	// transmitter
	private double txLoss;
	private double txDelay;
	private double txJitter;

	// receiver
	private double rxLoss;
	private double rxDelay;
	private double rxJitter;

	public NS3InputData(double txLoss, double txDelay, double txJitter, double rxLoss, double rxDelay, double rxJitter) {
		this.txLoss = txLoss;
		this.txDelay = txDelay;
		this.txJitter = txJitter;
		this.rxLoss = rxLoss;
		this.rxDelay = rxDelay;
		this.rxJitter = rxJitter;
	}

	public double getTxLoss() {
		return txLoss;
	}

	public void setTxLoss(double txLoss) {
		this.txLoss = txLoss;
	}

	public double getTxDelay() {
		return txDelay;
	}

	public void setTxDelay(double txDelay) {
		this.txDelay = txDelay;
	}

	public double getTxJitter() {
		return txJitter;
	}

	public void setTxJitter(double txJitter) {
		this.txJitter = txJitter;
	}

	public double getRxLoss() {
		return rxLoss;
	}

	public void setRxLoss(double rxLoss) {
		this.rxLoss = rxLoss;
	}

	public double getRxDelay() {
		return rxDelay;
	}

	public void setRxDelay(double rxDelay) {
		this.rxDelay = rxDelay;
	}

	public double getRxJitter() {
		return rxJitter;
	}

	public void setRxJitter(double rxJitter) {
		this.rxJitter = rxJitter;
	}

}
