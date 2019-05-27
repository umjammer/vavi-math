/*
 * Copyright (c) 2012 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math.factor.generator;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import vavi.math.StringUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * FactorTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2012/11/02 umjammer initial version <br>
 */
public class FactorGeneratorTest {

    static final String actual = "[2, 3, 3, 5, 5, 7, 11, 11, 11, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 17, 17, 17, 17, 17, 17, 19, 19, 23, 29, 29, 29, 31, 37, 41, 43, 43, 43, 43, 47, 53, 53, 53, 53, 53, 53, 53, 59, 59, 61, 67, 67, 67, 67, 67, 71, 73, 73, 73, 79, 79, 83, 83, 89, 89, 89, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 101, 101, 101, 101, 101, 101, 103, 103, 107, 109, 109, 109, 113, 127, 131, 137, 137, 137, 137, 139, 149, 149, 149, 149, 149, 149, 149, 151, 151, 157, 163, 163, 163, 163, 163, 167, 173, 173, 173, 173, 179, 179, 181, 181, 181, 191, 191, 191, 193, 193, 193, 193, 193, 193, 193, 197, 197, 197, 197, 197, 197, 199, 199, 211, 223, 223, 223, 227, 229, 233, 239, 239, 239, 239, 241, 251, 251, 251, 251, 251, 251, 251, 257, 257, 263, 269, 269, 269, 269, 269, 271, 277, 277, 277, 277, 277, 281, 281, 283, 283, 283, 283, 293, 293, 293, 311, 311, 311, 311, 311, 311, 313, 313, 317, 331, 331, 331, 337, 347, 349, 353, 353, 353, 353, 359, 367, 367, 367, 367, 367, 367, 367, 373, 373, 379, 383, 383, 383, 383, 383, 389, 397, 397, 397, 397, 397, 397, 401, 401, 409, 409, 409, 409, 409, 419, 419, 419, 421, 421, 421, 431, 431, 431, 431, 431, 431, 433, 433, 439, 443, 443, 443, 449, 457, 461, 463, 463, 463, 463, 467, 479, 479, 479, 479, 479, 479, 479, 487, 487, 491, 499, 499, 499, 499, 499, 503, 509, 509, 509, 509, 509, 509, 509, 521, 521, 523, 523, 523, 523, 523, 523, 541, 541, 541, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 547, 557, 557, 557, 557, 557, 557, 563, 563, 569, 571, 571, 571, 571, 577, 587, 593, 599, 599, 599, 599, 601, 607, 607, 607, 607, 607, 607, 607, 613, 613, 617, 619, 619, 619, 619, 619, 631, 641, 641, 641, 641, 641, 641, 641, 641, 643, 643, 647, 647, 647, 647, 647, 647, 647, 653, 653, 653, 659, 659, 659, 659, 659, 659, 659, 659, 659, 659, 659, 659, 661, 661, 661, 661, 661, 661, 673, 673, 677, 683, 683, 683, 683, 691, 701, 709, 719, 719, 719, 719, 727, 733, 733, 733, 733, 733, 733, 733, 739, 739, 743, 751, 751, 751, 751, 751, 757, 761, 761, 761, 761, 761, 761, 761, 761, 761, 769, 769, 773, 773, 773, 773, 773, 773, 773, 773, 787, 787, 787, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 797, 809, 809, 809, 809, 809, 809, 811, 811, 821, 823, 823, 823, 827, 829, 839, 853, 853, 853, 853, 857, 859, 859, 859, 859, 859, 859, 859, 863, 863, 877, 881, 881, 881, 881, 881, 883, 887, 887, 887, 887, 887, 887, 887, 887, 887, 887, 907, 907, 911, 911, 911, 911, 911, 911, 911, 911, 911, 919, 919, 919, 929, 929, 929, 929, 929, 929, 929, 929, 937, 937, 937, 937, 937, 937, 941, 941, 947, 953, 953, 953, 953, 967, 971, 977, 983, 983, 983, 983, 991, 997, 997, 997, 997, 997, 997, 997, 1009, 1009, 1013, 1019, 1019, 1019, 1019, 1019, 1021, 1031, 1031, 1031, 1031, 1031, 1031, 1031, 1031, 1031, 1031, 1031, 1033, 1033, 1039, 1039, 1039, 1039, 1039, 1039, 1039, 1039, 1039, 1039, 1049, 1049, 1049, 1051, 1051, 1051, 1061, 1061, 1061, 1061, 1061, 1061, 1063, 1063, 1069, 1087, 1087, 1087, 1091, 1093, 1097, 1103, 1103, 1103, 1103, 1109, 1117, 1117, 1117, 1117, 1117, 1117, 1117, 1123, 1123, 1129, 1151, 1151, 1151, 1151, 1151, 1153, 1163, 1163, 1163, 1163, 1163, 1163, 1163, 1163, 1163, 1163, 1163, 1163, 1171, 1171, 1181, 1181, 1181, 1181, 1181, 1181, 1181, 1181, 1181, 1181, 1181, 1187, 1187, 1187, 1193, 1193, 1193, 1193, 1193, 1193, 1201, 1201, 1201, 1201, 1201, 1201, 1213, 1213, 1217, 1223, 1223, 1223, 1223, 1229, 1231, 1237, 1249, 1249, 1249, 1249, 1259, 1277, 1277, 1277, 1277, 1277, 1277, 1277, 1279, 1279, 1283, 1289, 1289, 1289, 1289, 1289, 1291, 1297, 1297, 1297, 1297, 1297, 1297, 1297, 1297, 1297, 1297, 1297, 1297, 1297, 1301, 1301, 1303, 1303, 1303, 1303, 1303, 1303, 1303, 1303, 1303, 1303, 1303, 1303, 1307, 1307, 1307, 1319, 1319, 1319, 1319, 1319, 1319, 1319, 1319, 1321, 1321, 1321, 1321, 1321, 1321, 1327, 1327, 1361, 1367, 1367, 1367, 1367, 1373, 1381, 1399, 1409, 1409, 1409, 1409, 1423, 1427, 1427, 1427, 1427, 1427, 1427, 1427, 1429, 1429, 1433, 1439, 1439, 1439, 1439, 1439, 1447, 1451, 1451, 1451, 1451, 1451, 1451, 1451, 1451, 1451, 1451, 1451, 1451, 1451, 1451]";

    @Test
    public void test() {
        assertEquals(actual, StringUtil.toSequence(new FactorGenerator(new BigInteger("29581065270371479325227996328936846374431939693985901273918307582487327251714947685627979677677913536839844271841753057387727408177943031174593268995412065030592325959586779779083086141448624557019748194300914193739347851925459262380460776409945241666135623515576457155760725038527507713373195753840321861085031489701564491750744098878985806591816523081908294612831055277165585099711710058584112458084217699922468631143512544313357372238783837910116208934224815056462226666882530174655780271625913963384886674152874071941491472885863796958879196668985131706315088418589898196046067529593773549734928797370295738620574941857589823049829705846049461413969644779874433999329064371847480353568185103295523592054828279601510522112950624511845959364749007304372247726955205674380569791316902768622806080241058557308945337292198567858452155502456974367671040754562218149808838616722554477029020135967991906568229324759570892668089574211399791805222568952095995256252248902719526394425129893518226411894600368673452988892889242744668250530926131889228788353279891196002953863955036988620551780208216820904096713956573117875846262226458027340687748718556901237415338986663521515597576432050010490570504527907897009191488803056624918907967834455688168276594945342422959033609851517254390428810810323790475717971419367758903222554016377514377519519169723533860754878757838609665665451910531236989859315547896023826570664551604385678906166598725895539784981711730494441169160256011603297319575708034753802315037716713241128420321760899022236073573604466697940979583940022283052516619241882449486850919938716160277261512609372545701792279019667077356159457410658886724671519854857068411934463176224738604890946339083846826461651337533901515414436637401501770107202075869618193853996294474182147934132492506951195055315858141503877465911044521931727076612267111611092259163430235480690598804849118327628391206883654739630149776484521527377979176532763585292559027368943438718542722162269596527727942777552130604091187217185103566318788805986852987060631377812606355905860007387672453618153954170922891452510819586444258045366147848520158737104588535487342991983517189086079652855792142819089521183481046000133327452610029862345142900147219564761990589889344663281963416753513132112479837508605481341706361231865280534232116849067814201073431828182895925127899475576135468554103954262763021618348515222898644457768330388616944550795636836557304244204244020528397619212692591342044649864825568832453346114451256340883492092941868462221894307267118598466547706749550"))));
    }

    /** */
    public static void main(String[] args) throws Exception {
        BigInteger n = new BigInteger(args[0]);
        long t = System.currentTimeMillis();
        System.err.println(StringUtil.toSequence(new FactorGenerator(n)));
        System.err.printf("%s: in %d ms\n", n, System.currentTimeMillis() - t);
    }
}

/* */
