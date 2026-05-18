import java.util.Scanner;

public class ArenaPertarungan {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Musuh[] gelombangMonster = new Musuh [3];
        gelombangMonster[0] = new slime();
        gelombangMonster[1] = new naga();
        gelombangMonster[2] = new zombi();

        System.out.println("======================================");
        System.out.println("     ARENA RPG: GELOMBANG MONSTER     ");
        System.out.println("======================================\n");
        System.out.println("AWAS! Sekelompok monster menghadang Anda!");

        boolean isBermain = true;
        while (isBermain) {
            System.out.println("\n--- STATUS MONSTER ---");

            for(int i = 0; i < gelombangMonster.length; i++) {
                System.out.println((i + 1) + ". " + gelombangMonster[i].namaMusuh + " (HP: " + gelombangMonster[i].healthPoint + ")");
            }
            System.out.println("4. Kabur dari pertarungan");
            System.out.print("\nPilih target monster yang ingin diserang (1/2/3) atau 4 untuk kabur: ");
            
            try {
                int pilihanTarget = input.nextInt();

                if (pilihanTarget == 4) {
                    System.out.println("Anda lari terbirit-birit dari arena...");
                    isBermain = false;
                    continue; 
                }
                
                if (pilihanTarget < 1 || pilihanTarget > 3) {
                    System.out.println("Pilihan tidak valid! Anda membuang giliran.");
                } else {
                    System.out.print("Masukkan kekuatan serangan Anda (10 - 100): ");
                    int power = input.nextInt();

                    // PENGECEKAN POWER 
                    if (power < 10 || power > 100) {
                        // Lemparkan Custom Exception Anda secara sengaja di sini beserta pesannya!
                        throw new SeranganTidakValidException("Kekuatan serangan harus di antara 10 sampai 100!");
                    }

                    System.out.println("\n>>> HASIL SERANGAN ANDA <<<");

                    int indeksMonster = pilihanTarget - 1;
                    gelombangMonster[indeksMonster].terimaDamage(power);
                    
                    if (gelombangMonster[indeksMonster].healthPoint <= 0) {
                        System.out.println(gelombangMonster[indeksMonster].namaMusuh + " berhasil dikalahkan!");

                        if (gelombangMonster[indeksMonster] instanceof bisaloot) {
                            bisaloot monsterLoot = (bisaloot) gelombangMonster[indeksMonster];
                            monsterLoot.jatuhkanItem();
                        }
                    }
                }

                System.out.println("\n<<< GILIRAN MONSTER MEMBALAS >>>");
                for (int i = 0; i < gelombangMonster.length; i++) {
                    if (gelombangMonster[i].healthPoint > 0) {
                        Musuh monsterAktif = gelombangMonster[i];
                        monsterAktif.Bersuara();

                        if (monsterAktif instanceof BisaTerbang) {
                            System.out.println("[PERINGATAN! SERANGAN UDARA TERDETEKSI]");
                            BisaTerbang monsterTerbang = (BisaTerbang) monsterAktif;
                            monsterTerbang.lepasLandas();
                            monsterTerbang.seranganUdara();
                        } else {
                            monsterAktif.serangpemain();
                        }
                    } 
                }
                System.out.println("------------------------------------");

                // Cek kondisi
                boolean semuaMati = true;
                for (int i = 0; i < gelombangMonster.length; i++) {
                    if (gelombangMonster[i].healthPoint > 0) {
                        semuaMati = false;
                        break;
                    }
                }
                
                if (semuaMati) {
                    System.out.println("\nSELAMAT! Anda telah menyapu bersih gelombang monster ini!");
                    isBermain = false;
                }

            // MENANGKAP ERROR
            } catch (java.util.InputMismatchException e) {
                System.out.println("ERROR INPUT: Anda harus memasukkan ANGKA!");
                input.nextLine(); 
                
            

            //BLOK CATCH
            } catch (SeranganTidakValidException e) {
                System.out.println("KESALAHAN GAME: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("Terjadi kesalahan sistem: " + e.getMessage());
            }
        }
        
        input.close();
        System.out.println("Permainan Berakhir.");
    }
}