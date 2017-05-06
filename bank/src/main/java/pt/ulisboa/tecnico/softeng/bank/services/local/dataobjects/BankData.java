package pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.bank.domain.Operation;

public class BankData {
	public static enum CopyDepth {
		SHALLOW, CLIENTS, ACCOUNTS, OPERATIONS
	};

	private String name;
	private String code;
	private List<ClientData> clients = new ArrayList<>();
//	private List<AccountData> accounts = new ArrayList<>();
	private List<BankOperationData> operations = new ArrayList<>();

	public BankData() {
	}

	public BankData(Bank bank, CopyDepth depth) {
		this.name = bank.getName();
		this.code = bank.getCode();

		switch (depth) {
		case CLIENTS:
			for (Client client : bank.getClientSet()) {
				this.clients.add(new ClientData(client, ClientData.CopyDepth.SHALLOW));
			}
			break;
	/*	case ACCOUNTS:
			for (Account account : bank.getAccountSet()) {
				this.accounts.add(new AccountData(account));
			}
			break;*/
		case OPERATIONS:
			for (Operation operation : bank.getOperationSet()) {
				this.operations.add(new BankOperationData(operation));
			}
			break;
		case SHALLOW:
			break;
		default:
			break;
		}

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<ClientData> getClients() {
		return clients;
	}

	public void setClients(List<ClientData> clients) {
		this.clients = clients;
	}

	public List<BankOperationData> getOperations() {
		return operations;
	}

	public void setOperations(List<BankOperationData> operations) {
		this.operations = operations;
	}
}
