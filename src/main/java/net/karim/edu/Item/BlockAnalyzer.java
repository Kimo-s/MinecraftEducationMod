package net.karim.edu.Item;

import net.karim.edu.EduChemMod;
import net.karim.edu.screen.BlockAnalyzerScreenHandler;
import net.karim.edu.screen.Cotton.BlockAnalyzerCotton;
import net.karim.edu.screen.Cotton.BlockAnalyzerCottonScreen;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class BlockAnalyzer extends Item {

    final static Block[] blocks = new Block[]{
            Blocks.DIRT,
            Blocks.STONE,
            Blocks.ACACIA_WOOD
    };
    final static String[] blockElements = new String[]{
            "Iron (Fe), Oxygen (O2), Potassium (K)",
            "Iron (Fe), Oxygen (O2), Phosphorus (P)",
            "Oxygen (O2), Sulfur (S), Hydrogen (H)"
    };

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
                context.getPlayer().sendMessage(Text.of("Elements in " + hitBlock.getName() + ": "  + blockElements[i]));
                if(context.getWorld().isClient){
                    MinecraftClient.getInstance().setScreen(new BlockAnalyzerCotton(new BlockAnalyzerCottonScreen("Elements in " + hitBlock.getName() + ": "  + blockElements[i])));
                }
                break;
            }
        }
        TagKey<Block> key = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "logs"));



//        if (!context.getWorld().isClient) {
//            NamedScreenHandlerFactory screenHandlerFactory = new BlockAnalyzerScreenHandler(0, context.getPlayer().getInventory());
//
//            if (screenHandlerFactory != null) {
//                context.getPlayer().openHandledScreen(screenHandlerFactory);
//            }
//        }


        return super.useOnBlock(context);
    }
}
