package block01;



import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class Main {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 3;

	public static void main(String[] args) {	
	
		
		System.out.println("Mining block 1... ");
		addBlock(new Block("first block", "0"));
		
		System.out.println("Mining block 2... ");
		addBlock(new Block("second block",blockchain.get(blockchain.size()-1).hash));
		
		System.out.println("Mining block 3... ");
		addBlock(new Block("third block",blockchain.get(blockchain.size()-1).hash));	
		
		System.out.println("\nBlockchain is Valid: " + isChainValid());
		
		String blockchainJson = BlockUtil.getJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
		
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
	
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
			
		}
		return true;
	}
	
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
}
