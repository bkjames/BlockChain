package BlockChain02;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;



public class Main {
	
	public static ArrayList<Block> blockChain = new ArrayList<>();//[1,2,3,...]
	public static int difficulty =2;
	public static HashMap<String, TransactionOutput> UTXOs = new HashMap<>();
	
	public static Wallet walletA;
	public static Wallet walletB;
	public static Transaction genesisTransaction;
	public static float mininmumTransaction =0.1f;
	
	public static void main(String[] args) {
		System.out.println("test\n");
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		//1. 지갑 생성
		walletA = new Wallet();
		walletB = new Wallet();
		Wallet coinbase = new Wallet();
		
		//2. 제너시스 트랜잭션 생성
		genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 100f, null);
		genesisTransaction.generateSignature(coinbase.privateKey);
		genesisTransaction.transactionId="0";
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value, genesisTransaction.transactionId));
		//3. main save utxo
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		
		//4. genesis 생성
		Block genesisBlock = new Block("0"); 
		genesisBlock.addTransaction(genesisTransaction);
		addBlock(genesisBlock);
		
		//5.다음 블록 생성
		Block block1 = new Block(genesisBlock.hash);
		System.out.println("first "+walletA.getBalance());
		Transaction tx = walletA.sendFunds(walletB.publicKey, 40f);
		System.out.println("block1 add 전 "+walletA.getBalance());
		block1.addTransaction(tx);
		System.out.println("block1 add 후 "+walletA.getBalance());
		addBlock(block1);
	
	}
	
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockChain.add(newBlock);
	}


}
