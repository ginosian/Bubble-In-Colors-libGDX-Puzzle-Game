package com.martha.bubble.controller;

import com.badlogic.gdx.graphics.Color;
import com.martha.bubble.actor.Pallet;
import com.martha.bubble.enums.ColorPallet;
import com.martha.bubble.util.Util;

import java.util.LinkedHashMap;

/**
 * Created by Martha on 11/4/2015.
 */
public class ColorController {
    // region Instance Fields
    private LinkedHashMap<ColorPallet, Pallet> pallets;
    // endregion

    // region Ctor
    public ColorController() {
        pallets = new LinkedHashMap<ColorPallet, Pallet>();
        initColorPallets();
    }
    // endregion

    // region Member Methods
    public Pallet randomPallet(){ // TODO Change after pallet final setup
        int idx = Util.random.nextInt(pallets.size()) + 1;
        return pallets.get(ColorPallet.valueOf("PALLET_" + idx));
    }
    public void initColorPallets() {    // SUPER hard code for colors. :)
        this.pallets.put(ColorPallet.PALLET_1, new Pallet //
            (Color.valueOf("FFBE45"), Color.valueOf("027878"), Color.valueOf("9CBF2A"), Color.valueOf("F37338"), Color.valueOf("C22326")));
        this.pallets.put(ColorPallet.PALLET_2, new Pallet //
                (Color.valueOf("FF00AA"), Color.valueOf("AA00FF"), Color.valueOf("00AAFF"), Color.valueOf("FCAA00"), Color.valueOf("AAFF01")));
        this.pallets.put(ColorPallet.PALLET_3, new Pallet //
                (Color.valueOf("017890"), Color.valueOf("E95D22"), Color.valueOf("555555"), Color.valueOf("98CB4A"), Color.valueOf("FDB632")));
        this.pallets.put(ColorPallet.PALLET_4, new Pallet //
                (Color.valueOf("FFFF00"), Color.valueOf("3C3CC9"), Color.valueOf("00FFFF"), Color.valueOf("FF00FF"), Color.valueOf("00FF00")));
        this.pallets.put(ColorPallet.PALLET_5, new Pallet //
                (Color.valueOf("F7D842"), Color.valueOf("5481E6"), Color.valueOf("F15F74"), Color.valueOf("97C30A"), Color.valueOf("C0BCB6")));
        this.pallets.put(ColorPallet.PALLET_6, new Pallet //
                (Color.valueOf("F7D842"), Color.valueOf("A6EE6E"), Color.valueOf("FF7A5A"), Color.valueOf("8ED2C9"), Color.valueOf("AF80D1")));
        this.pallets.put(ColorPallet.PALLET_7, new Pallet // //
                (Color.valueOf("7d6b66"), Color.valueOf("cad5d7"), Color.valueOf("eaa2b8"), Color.valueOf("63c3c2"), Color.valueOf("d94f66")));
        this.pallets.put(ColorPallet.PALLET_8, new Pallet // //
                (Color.valueOf("fdf8bc"), Color.valueOf("b1bee0"), Color.valueOf("7F5EBD"), Color.valueOf("ffb791"), Color.valueOf("5f485a")));
        this.pallets.put(ColorPallet.PALLET_9, new Pallet //
                (Color.valueOf("FDB632"), Color.valueOf("4DB3B3"), Color.valueOf("839098"), Color.valueOf("98CB4A"), Color.valueOf("E64A45")));
        this.pallets.put(ColorPallet.PALLET_10, new Pallet //
                (Color.valueOf("96FF00"), Color.valueOf("009600"), Color.valueOf("966400"), Color.valueOf("FF6400"), Color.valueOf("3A3AC7")));
        this.pallets.put(ColorPallet.PALLET_11, new Pallet //
                (Color.valueOf("FFB85F"), Color.valueOf("017890"), Color.valueOf("DF7782"), Color.valueOf("C0BCB6"), Color.valueOf("A83707")));
        this.pallets.put(ColorPallet.PALLET_12, new Pallet //
                (Color.valueOf("FFB85F"), Color.valueOf("00A99F"), Color.valueOf("FF7A5A"), Color.valueOf("98CB4A"), Color.valueOf("8ED2C9")));
        this.pallets.put(ColorPallet.PALLET_13, new Pallet // hasa aystex
                (Color.valueOf("E6772E"), Color.valueOf("4DB3B3"), Color.valueOf("F2C249"), Color.valueOf("AF80D1"), Color.valueOf("E64A45")));
        this.pallets.put(ColorPallet.PALLET_14, new Pallet //
                (Color.valueOf("F6EC8D"), Color.valueOf("4ED9D2"), Color.valueOf("F7F0E6"), Color.valueOf("3AB54B"), Color.valueOf("FF7568")));
        this.pallets.put(ColorPallet.PALLET_15, new Pallet //
                (Color.valueOf("F2B819"), Color.valueOf("49B0AD"), Color.valueOf("F1611A"), Color.valueOf("B1A99E"), Color.valueOf("E91C57")));
        this.pallets.put(ColorPallet.PALLET_16, new Pallet //
                (Color.valueOf("F2C40F"), Color.valueOf("0054A5"), Color.valueOf("E77E22"), Color.valueOf("00AFF0"), Color.valueOf("3AB54B")));
        this.pallets.put(ColorPallet.PALLET_17, new Pallet //
                (Color.valueOf("FFA300"), Color.valueOf("E84C3D"), Color.valueOf("3AB54B"), Color.valueOf("00AFF0"), Color.valueOf("0054A5")));
        this.pallets.put(ColorPallet.PALLET_18, new Pallet //
                (Color.valueOf("FFA300"), Color.valueOf("13A8FE"), Color.valueOf("CF0060"), Color.valueOf("FF00FF"), Color.valueOf("009600")));
        this.pallets.put(ColorPallet.PALLET_19, new Pallet // //
                (Color.valueOf("4ED9D2"), Color.valueOf("9055a5"), Color.valueOf("dea9cc"), Color.valueOf("807e57"), Color.valueOf("431abe")));
        this.pallets.put(ColorPallet.PALLET_20, new Pallet // //
                (Color.valueOf("b65baa"), Color.valueOf("6377a9"), Color.valueOf("A6EE6E"), Color.valueOf("c7a7bc"), Color.valueOf("f9dba9")));
        this.pallets.put(ColorPallet.PALLET_21, new Pallet //
                (Color.valueOf("DF7782"), Color.valueOf("9FB143"), Color.valueOf("EBC338"), Color.valueOf("356A61"), Color.valueOf("FC7E3E")));
        this.pallets.put(ColorPallet.PALLET_22, new Pallet //
                (Color.valueOf("FFDD55"), Color.valueOf("E84C3D"), Color.valueOf("3CB194"), Color.valueOf("FF76AA"), Color.valueOf("98AA54")));
        this.pallets.put(ColorPallet.PALLET_23, new Pallet
                (Color.valueOf("ffc7ba"), Color.valueOf("40456f"), Color.valueOf("d45d5f"), Color.valueOf("84586f"), Color.valueOf("d8d0e0")));
        this.pallets.put(ColorPallet.PALLET_24, new Pallet //
                (Color.valueOf("FFCB00"), Color.valueOf("009E2F"), Color.valueOf("873CBE"), Color.valueOf("E62937"), Color.valueOf("0079F1")));
        this.pallets.put(ColorPallet.PALLET_25, new Pallet
                (Color.valueOf("eb41a2"), Color.valueOf("8ca1c0"), Color.valueOf("294f73"), Color.valueOf("a11858"), Color.valueOf("e3ebfe")));
        this.pallets.put(ColorPallet.PALLET_26, new Pallet //
                (Color.valueOf("FDF900"), Color.valueOf("FF7525"), Color.valueOf("00E430"), Color.valueOf("00A9FF"), Color.valueOf("CD86FF")));
        this.pallets.put(ColorPallet.PALLET_27, new Pallet // //
                (Color.valueOf("949b4b"), Color.valueOf("ff8103"), Color.valueOf("496580"), Color.valueOf("870721"), Color.valueOf("ff3636")));
        this.pallets.put(ColorPallet.PALLET_28, new Pallet //
                (Color.valueOf("CA5885"), Color.valueOf("E2B9B5"), Color.valueOf("3BB294"), Color.valueOf("899C2E"), Color.valueOf("F2C40F")));
        this.pallets.put(ColorPallet.PALLET_29, new Pallet //
                (Color.valueOf("E25733"), Color.valueOf("014E83"), Color.valueOf("BEC367"), Color.valueOf("019272"), Color.valueOf("B41931")));
        this.pallets.put(ColorPallet.PALLET_30, new Pallet
                (Color.valueOf("efb8e2"), Color.valueOf("536f61"), Color.valueOf("d6789c"), Color.valueOf("7d7ebf"), Color.valueOf("a8e6c1")));
        this.pallets.put(ColorPallet.PALLET_31, new Pallet
                (Color.valueOf("d7ae44"), Color.valueOf("66aaab"), Color.valueOf("ba725a"), Color.valueOf("cee9f0"), Color.valueOf("c80002")));
        this.pallets.put(ColorPallet.PALLET_32, new Pallet
                (Color.valueOf("fff787"), Color.valueOf("5965b1"), Color.valueOf("f3ca9c"), Color.valueOf("f25510"), Color.valueOf("222e6a")));
        this.pallets.put(ColorPallet.PALLET_33, new Pallet //
                (Color.valueOf("FD3E61"), Color.valueOf("3BB294"), Color.valueOf("027878"), Color.valueOf("E0D04D"), Color.valueOf("8E850C")));
        this.pallets.put(ColorPallet.PALLET_34, new Pallet //
                (Color.valueOf("6d8683"), Color.valueOf("93173c"), Color.valueOf("d18d80"), Color.valueOf("ebd693"), Color.valueOf("a59bc0")));
        this.pallets.put(ColorPallet.PALLET_35, new Pallet //
          (Color.valueOf("fced14"), Color.valueOf("e0ebf1"), Color.valueOf("09dfe9"), Color.valueOf("833660"), Color.valueOf("322c3a")));
        this.pallets.put(ColorPallet.PALLET_36, new Pallet //
          (Color.valueOf("df0913"), Color.valueOf("b0b630"), Color.valueOf("488891"), Color.valueOf("c7e2ed"), Color.valueOf("09dfe9")));
        this.pallets.put(ColorPallet.PALLET_37, new Pallet //
          (Color.valueOf("09dfe9"), Color.valueOf("b44c2f"), Color.valueOf("b0b630"), Color.valueOf("4f5350"), Color.valueOf("fbfbfd")));
        this.pallets.put(ColorPallet.PALLET_38, new Pallet //
          (Color.valueOf("748494"), Color.valueOf("735f5d"), Color.valueOf("b0b630"), Color.valueOf("ebd693"), Color.valueOf("7a0810")));
        this.pallets.put(ColorPallet.PALLET_39, new Pallet //
          (Color.valueOf("98020e"), Color.valueOf("b0b630"), Color.valueOf("acdffe"), Color.valueOf("1241cd"), Color.valueOf("4f5350")));
        this.pallets.put(ColorPallet.PALLET_40, new Pallet //
         (Color.valueOf("fced14"), Color.valueOf("6f9b80"), Color.valueOf("f18b7c"), Color.valueOf("f0f4f7"), Color.valueOf("bb1009")));
        this.pallets.put(ColorPallet.PALLET_41, new Pallet //
          (Color.valueOf("fafafc"), Color.valueOf("8b8faa"), Color.valueOf("fbff77"), Color.valueOf("82e8fe"), Color.valueOf("4f5350")));
        this.pallets.put(ColorPallet.PALLET_42, new Pallet //
          (Color.valueOf("4f5350"), Color.valueOf("507808"), Color.valueOf("dcdedd"), Color.valueOf("b56655"), Color.valueOf("6f1c0c")));
        this.pallets.put(ColorPallet.PALLET_43, new Pallet //
          (Color.valueOf("f70000"), Color.valueOf("820202"), Color.valueOf("3b78a1"), Color.valueOf("b0b630"), Color.valueOf("c2d5db")));
        this.pallets.put(ColorPallet.PALLET_44, new Pallet //
          (Color.valueOf("96bbc4"), Color.valueOf("6d864f"), Color.valueOf("a88862"), Color.valueOf("ac252c"), Color.valueOf("cdbba7")));
        this.pallets.put(ColorPallet.PALLET_45, new Pallet //
          (Color.valueOf("a8d300"), Color.valueOf("b2cae6"), Color.valueOf("ba2b57"), Color.valueOf("ff9ccc"), Color.valueOf("682336")));
        this.pallets.put(ColorPallet.PALLET_46, new Pallet //
          (Color.valueOf("9cba0d"), Color.valueOf("350a63"), Color.valueOf("6f9b80"), Color.valueOf("ff859d"), Color.valueOf("8d0020")));
        this.pallets.put(ColorPallet.PALLET_47, new Pallet //
          (Color.valueOf("f7f389"), Color.valueOf("853F5D"), Color.valueOf("db916e"), Color.valueOf("977aba"), Color.valueOf("484F91")));
        this.pallets.put(ColorPallet.PALLET_48, new Pallet //
          (Color.valueOf("f3d529"), Color.valueOf("742633"), Color.valueOf("84693a"), Color.valueOf("6f9b80"), Color.valueOf("574075")));
        this.pallets.put(ColorPallet.PALLET_49, new Pallet //
          (Color.valueOf("431abe"), Color.valueOf("807e57"), Color.valueOf("9055a5"), Color.valueOf("dea9cc"), Color.valueOf("fffdff")));
        this.pallets.put(ColorPallet.PALLET_50, new Pallet //
          (Color.valueOf("fcc9f2"), Color.valueOf("728073"), Color.valueOf("814282"), Color.valueOf("6416B8"), Color.valueOf("d4f5ee")));
        this.pallets.put(ColorPallet.PALLET_51, new Pallet //
          (Color.valueOf("da9432"), Color.valueOf("5c6b28"), Color.valueOf("e7e8ab"), Color.valueOf("67998B"), Color.valueOf("8c4511")));
        this.pallets.put(ColorPallet.PALLET_52, new Pallet //
          (Color.valueOf("6d864f"), Color.valueOf("fffab5"), Color.valueOf("eec9fd"), Color.valueOf("ba58bb"), Color.valueOf("b8d5f3")));
        this.pallets.put(ColorPallet.PALLET_53, new Pallet //
          (Color.valueOf("b7acd7"), Color.valueOf("3cdef5"), Color.valueOf("eaa2b8"), Color.valueOf("f5deeb"), Color.valueOf("C95F9D")));
        this.pallets.put(ColorPallet.PALLET_54, new Pallet //
          (Color.valueOf("fbf38c"), Color.valueOf("e4a227"), Color.valueOf("58a8a5"), Color.valueOf("6B403C"), Color.valueOf("1b2663")));
        this.pallets.put(ColorPallet.PALLET_55, new Pallet //
          (Color.valueOf("eb56a7"), Color.valueOf("993549"), Color.valueOf("d2ada4"), Color.valueOf("fffdff"), Color.valueOf("7F5EBD")));
        this.pallets.put(ColorPallet.PALLET_56, new Pallet //
          (Color.valueOf("F7F0E6"), Color.valueOf("526754"), Color.valueOf("574747"), Color.valueOf("a26262"), Color.valueOf("c3a99c")));
        this.pallets.put(ColorPallet.PALLET_57, new Pallet //
          (Color.valueOf("84586f"), Color.valueOf("5aadb5"), Color.valueOf("fffab5"), Color.valueOf("52546b"), Color.valueOf("cfc2ba")));
        this.pallets.put(ColorPallet.PALLET_58, new Pallet //
          (Color.valueOf("f3e2d8"), Color.valueOf("fde64a"), Color.valueOf("7EC7C1"), Color.valueOf("32587c"), Color.valueOf("9b5e4b")));
        this.pallets.put(ColorPallet.PALLET_59, new Pallet //
            (Color.valueOf("9FB07B"), Color.valueOf("483799"), Color.valueOf("fd0f27"), Color.valueOf("ade3d3"), Color.valueOf("53bfb2")));
//






    }
    // endregion
}




