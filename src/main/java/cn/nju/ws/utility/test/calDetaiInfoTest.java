package cn.nju.ws.utility.test;

import cn.nju.ws.unit.instSet.Inst;

import static cn.nju.ws.utility.ParamDef.doc1;
import static cn.nju.ws.utility.ParamDef.doc2;

/**
 * Created by ciferlv on 17-6-13.
 */
public class calDetaiInfoTest {

    public static void calDetailInfo() {

        Inst inst1 = doc1.getGraph().get("http://data.doremus.org/expression/bb2a7dfc-7a33-3b4a-9c0e-e8b13ff481da");
        Inst inst2 = doc2.getGraph().get("http://data.doremus.org/expression/170bca3e-9600-3565-959d-b1873416e1b7");

    }
}
