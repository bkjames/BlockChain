package BlockChain02;

import java.util.ArrayList;
import java.util.Date;

//1. 항목이 있어야한다.
//2. 항목의 기능이 있어야 한다.
public class Block {
	
	public String hash; //"0000157", 인간의 지문(고유한키값)
	public String previousHash ;// 123 이전 인간에 대한 지문값
	public String data; //값을 담는 부분 전송내역...
	public int nonce; //1000,1221 random
	public long timeStamp;//12.2
	public String merkleRoot;
	
	public ArrayList<Transaction> transactionList = new ArrayList<>();
	
	
    
	public Block( String preHash) {
    	this.previousHash =preHash;
    	this.timeStamp = new Date().getTime();//20190210121212
    	this.hash = calculateHash();
	}


	public String calculateHash() {
    	String hash= BlockUtil.applySha256(previousHash+Long.toString(timeStamp)+Integer.toString(nonce)+merkleRoot);
    	return hash;
    }
    
    public void mineBlock(int diff) {
    	merkleRoot = BlockUtil.getMerkleRoot(transactionList);
    	String target = BlockUtil.getDifficultyString(diff);//2이면 앞자리가 00
    	while(!hash.substring(0,diff).equals(target)) {
    		nonce++;
    		hash = calculateHash();
//    	  	System.out.println("target: "+target+" hash: "+hash+" nonce: "+nonce);
    	}
//    	System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$Mined hash: "+hash+" nonce: "+nonce);
    	
    }
    
    public boolean addTransaction(Transaction tx) {
    	if(tx == null) return false;
    	if(previousHash != "0") {
    		if(tx.processTransaction() != true) {
    			return false;
    		}
    	}
    	transactionList.add(tx);
    	return true;
    }
	
	
	

}
