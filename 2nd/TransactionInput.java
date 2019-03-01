package Part2;

public class TransactionInput {
	public String transactionOutputId; // TransactionOutputs -> transactionId
	public TransactionOutput UTXO; //Unspent transaction output
	
	public TransactionInput(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}
}
