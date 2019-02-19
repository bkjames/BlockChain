package BlockChain02;

import java.security.PublicKey;

public class TransactionOutput {

	public String id;
	public PublicKey reciepient;
	public float value;
	public String parentTransactionId;
	
	public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId) {
		this.reciepient = reciepient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		this.id = BlockUtil.applySha256(BlockUtil.getStringFromKey(reciepient)+Float.toString(value)+parentTransactionId);
	}
	
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == reciepient);
	}
}
