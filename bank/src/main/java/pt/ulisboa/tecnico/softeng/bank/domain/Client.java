package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class Client {
	private static int counter = 0;

	private final String name;
	private final String ID;

	public Client(Bank bank, String name) {
		if (bank == null || name == null) {
			throw new BankException("null argument");
		}

		checkName(name);

		this.ID = Integer.toString(++Client.counter);
		this.name = name;

		bank.addClient(this);
	}

	private void checkName(String name) {
		if (name.trim().length() == 0) {
			throw new BankException("No bank name");
		}
	}

	public String getName() {
		return this.name;
	}

	public String getID() {
		return this.ID;
	}

}
