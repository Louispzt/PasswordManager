package utils;

import java.io.IOException;
import java.math.BigInteger;

public class test {
    public static void main(String[] args) throws IOException {
        RSA rsa = new RSA("test", 512);
        System.out.println(rsa.decode("64281811800523870326809364243536295001912830998716133965510232113209846593826628616065954528025524579142534588241749802035459168591437742856654073900730433274883724143096837066322026385102053049765572297772780994879597800873588575075433837610775402322865142355427162371371703211025045693725278614091101244229 and 133490099066072958398702215210511949050331974723473135484807923931422363558440481600379359054255508752705250583388477465523534123011991369450376293477868573413099123648922039697342899827083134781057333461523070484818979187861228088717794378156749890874042898405503078958280348603381631879049794768936473040541 and 104756768458176209335460682032300084498103149248380049707122013779643267894517762959426888049424275851616074649805565141576168854416986795520575986002781326692895993949375841361524656681511288653390125836189381362292363836891108002486705833946321744220545705229049172691139363506738075788732863595077971473327"));
        //SaveFile saveFile = new SaveFile(rsa);
        //saveFile.save();
    }
}