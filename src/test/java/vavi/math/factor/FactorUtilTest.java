/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.math.factor;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * FactorUtilTest. 
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/02/12 umjammer initial version <br>
 */
public class FactorUtilTest {

    @Test
    public void test() {
        assertEquals(6857, FactorUtil.maxFactor(new BigInteger("600851475143")).intValue());
System.err.println(FactorUtil.maxFactor(new BigInteger("29581065270371479325227996328936846374431939693985901273918307582487327251714947685627979677677913536839844271841753057387727408177943031174593268995412065030592325959586779779083086141448624557019748194300914193739347851925459262380460776409945241666135623515576457155760725038527507713373195753840321861085031489701564491750744098878985806591816523081908294612831055277165585099711710058584112458084217699922468631143512544313357372238783837910116208934224815056462226666882530174655780271625913963384886674152874071941491472885863796958879196668985131706315088418589898196046067529593773549734928797370295738620574941857589823049829705846049461413969644779874433999329064371847480353568185103295523592054828279601510522112950624511845959364749007304372247726955205674380569791316902768622806080241058557308945337292198567858452155502456974367671040754562218149808838616722554477029020135967991906568229324759570892668089574211399791805222568952095995256252248902719526394425129893518226411894600368673452988892889242744668250530926131889228788353279891196002953863955036988620551780208216820904096713956573117875846262226458027340687748718556901237415338986663521515597576432050010490570504527907897009191488803056624918907967834455688168276594945342422959033609851517254390428810810323790475717971419367758903222554016377514377519519169723533860754878757838609665665451910531236989859315547896023826570664551604385678906166598725895539784981711730494441169160256011603297319575708034753802315037716713241128420321760899022236073573604466697940979583940022283052516619241882449486850919938716160277261512609372545701792279019667077356159457410658886724671519854857068411934463176224738604890946339083846826461651337533901515414436637401501770107202075869618193853996294474182147934132492506951195055315858141503877465911044521931727076612267111611092259163430235480690598804849118327628391206883654739630149776484521527377979176532763585292559027368943438718542722162269596527727942777552130604091187217185103566318788805986852987060631377812606355905860007387672453618153954170922891452510819586444258045366147848520158737104588535487342991983517189086079652855792142819089521183481046000133327452610029862345142900147219564761990589889344663281963416753513132112479837508605481341706361231865280534232116849067814201073431828182895925127899475576135468554103954262763021618348515222898644457768330388616944550795636836557304244204244020528397619212692591342044649864825568832453346114451256340883492092941868462221894307267118598466547706749550")).intValue());
        assertEquals(87, FactorUtil.maxFactor(new BigInteger("29581065270371479325227996328936846374431939693985901273918307582487327251714947685627979677677913536839844271841753057387727408177943031174593268995412065030592325959586779779083086141448624557019748194300914193739347851925459262380460776409945241666135623515576457155760725038527507713373195753840321861085031489701564491750744098878985806591816523081908294612831055277165585099711710058584112458084217699922468631143512544313357372238783837910116208934224815056462226666882530174655780271625913963384886674152874071941491472885863796958879196668985131706315088418589898196046067529593773549734928797370295738620574941857589823049829705846049461413969644779874433999329064371847480353568185103295523592054828279601510522112950624511845959364749007304372247726955205674380569791316902768622806080241058557308945337292198567858452155502456974367671040754562218149808838616722554477029020135967991906568229324759570892668089574211399791805222568952095995256252248902719526394425129893518226411894600368673452988892889242744668250530926131889228788353279891196002953863955036988620551780208216820904096713956573117875846262226458027340687748718556901237415338986663521515597576432050010490570504527907897009191488803056624918907967834455688168276594945342422959033609851517254390428810810323790475717971419367758903222554016377514377519519169723533860754878757838609665665451910531236989859315547896023826570664551604385678906166598725895539784981711730494441169160256011603297319575708034753802315037716713241128420321760899022236073573604466697940979583940022283052516619241882449486850919938716160277261512609372545701792279019667077356159457410658886724671519854857068411934463176224738604890946339083846826461651337533901515414436637401501770107202075869618193853996294474182147934132492506951195055315858141503877465911044521931727076612267111611092259163430235480690598804849118327628391206883654739630149776484521527377979176532763585292559027368943438718542722162269596527727942777552130604091187217185103566318788805986852987060631377812606355905860007387672453618153954170922891452510819586444258045366147848520158737104588535487342991983517189086079652855792142819089521183481046000133327452610029862345142900147219564761990589889344663281963416753513132112479837508605481341706361231865280534232116849067814201073431828182895925127899475576135468554103954262763021618348515222898644457768330388616944550795636836557304244204244020528397619212692591342044649864825568832453346114451256340883492092941868462221894307267118598466547706749550")).intValue());
    }
}

/* */
