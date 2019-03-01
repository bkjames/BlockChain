package Part2;

import java.util.ArrayList;

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
	public static float minimumTransaction =0.1f;
						
	public static void main(String[] args) {
	
		
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
		System.out.println("");
				
		//5.다음 블록(Block1) 생성
		Block block1 = new Block(genesisBlock.hash);
		System.out.println("1. walletA.getBalance(): "+walletA.getBalance());
		System.out.println("2. block1 add 전 "+walletA.getBalance());
		
		block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f));
		addBlock(block1);
		System.out.println("3. walletA.getBalance(): "+walletA.getBalance());
		System.out.println("4. walletB.getBalance(): "+walletB.getBalance());

	
	}
	
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockChain.add(newBlock);
	}


}
