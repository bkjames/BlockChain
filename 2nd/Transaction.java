package BlockChain02;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
	public String transactionId;
	public PublicKey sender;
	public PublicKey reciepient;
	public float value;
	public byte[] signature;
	
	public ArrayList<TransactionInput> inputs = new ArrayList<>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<>();
	
	public static int sequence =0;
	
	public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.inputs = inputs;
	}
	
	public boolean processTransaction() {
		
		//1.verifySignature()
		if(verifySignature()==false) {
			return false;
		}
		
		//2. 메인함수에 임시 저장된 txoutId로 UTXO(사용되지 않은게 맞냐? 확인)
		for(TransactionInput i :inputs) {
			i.UTXO = Main.UTXOs.get(i.transactionOutputId);
		}
		
		//3. 최소단위 0.1f 를 넘는지 체크
		if(getInputValue() < Main.mininmumTransaction) {
			return false;
		}
		
		//4. TxOutput 100 value: 40,60
		float leftOver = getInputValue()-value;
		
		transactionId = calculateHash();
		//40코인 송신
		outputs.add(new TransactionOutput(this.reciepient, value, transactionId));
		//나머지 60
		outputs.add(new TransactionOutput(this.sender, leftOver, transactionId));
		
		
		//5. output to Unspent list
		for(TransactionOutput o : outputs) {
			Main.UTXOs.put(o.id, o);
		}
		
		// 6. remove Txinput
		for(TransactionInput i : inputs) {
			if(i.UTXO == null)continue;
			Main.UTXOs.remove(i.UTXO.id);
		}
	
		return true;
	}
	
	public String calculateHash() {
		sequence++;
		return BlockUtil.applySha256(BlockUtil.getStringFromKey(sender)+ 
				BlockUtil.getStringFromKey(reciepient)+
				Float.toString(value)+sequence);
	}
	
	public float getInputValue() {
		float total=0;
		for(TransactionInput i : inputs) {
			if(i.UTXO == null) continue;
			total += i.UTXO.value;
		}
		return total;
	}
	
	public boolean verifySignature() {
		String data = BlockUtil.getStringFromKey(sender)+BlockUtil.getStringFromKey(reciepient)+Float.toString(value);
		return BlockUtil.verifyECDSASig(sender, data, signature);
	}
	public void generateSignature(PrivateKey privateKey) {
		String data = BlockUtil.getStringFromKey(sender)+BlockUtil.getStringFromKey(reciepient)+Float.toString(value);
		signature = BlockUtil.applyECDSASig(privateKey, data);
		
	}

}
