package net.karim.edu.Item;

import net.karim.edu.EduChemMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class BlockAnalyzer extends Item {

    final static Block[] blocks = new Block[]{Blocks.DIRT, Blocks.STONE};
    final static String[] blockElements = new String[]{"Iron (Fe), Oxygen (O2), Potassium (K)", "Iron (Fe), Oxygen (O2), Phosphorus (P)"};

    public BlockAnalyzer(Settings settings) {
        super(settings);
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        if(blockElements.length != blocks.length){
            EduChemMod.LOGGER.info("Mismatch between blocks and their descriptions inside block analyzer item.");
            return ActionResult.FAIL;
        }

        for(int i = 0; i < blocks.length; i++) {
            Block hitBlock = context.getWorld().getBlockState(context.getBlockPos()).getBlock();
            if(hitBlock == blocks[i]){
                context.getPlayer().sendMessage(Text.of("Elements in the block: " + blockElements[i]));
                break;
            }
        }
        return super.useOnBlock(context);
    }
}
