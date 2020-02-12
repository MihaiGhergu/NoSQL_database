package tema2poo;
import java.io.*;
import java.text.*;

public class Tema2 {
    public static void main(String[] args) throws ParseException {
        File fileName = new File(args[0]);/*fisier input*/
        File fileOut = new File(fileName + "_out");/*fisier output*/
        PrintWriter out = null;      
        BufferedReader reader = null;
        String line,Entity,Db_Name;
        String[] tok;
        int No_Nodes,Max_Capacity, RF,No_Attributes;
        DataBase d=null;
        Entitate e;
        Atribut a;
        try{
            reader = new BufferedReader(new FileReader(fileName));
            out = new PrintWriter(fileOut);
            /*citesc fiecare linie*/
            while(true){
                line = reader.readLine();
                if(line == null)
                    break;
                /*vector cu stringurile de pe fiecare linie*/
                tok = line.split(" ");
                /*crearea bazei de date*/
                if(tok[0].equals("CREATEDB")){
                    Db_Name = tok[1];
                    No_Nodes = Integer.parseInt(tok[2]);
                    Max_Capacity = Integer.parseInt(tok[3]);
                    d = new DataBase(Db_Name,No_Nodes,Max_Capacity);
                }
                /*crearea unei entitati*/
                if(tok[0].equals("CREATE")){
                    Entity = tok[1];
                    RF = Integer.parseInt(tok[2]);
                    No_Attributes = Integer.parseInt(tok[3]);
                    e = new Entitate(Entity,RF,No_Attributes);
                    for(int i=4; i < tok.length; i+=2){
                        a = new Atribut(tok[i],tok[i+1]);
                        e.getAttributes().add(a);
                    }
                    d.getEntities().add(e);
                }
                
                /*Crearea unei instante*/
                if(tok[0].equals("INSERT")){
                    Entity = tok[1];
                    for(Entitate entitie : d.getEntities()){
                        if(entitie.getName().equals(Entity)){
                            int rf = entitie.getrFactor();
                            Instanta instantaMea = new Instanta(Entity);
                            int i=2;
                            for(Atribut atr : entitie.getAttributes()){
                                /*cast la integer*/
                                if(atr.getType().equals("Integer")){
                                     int intAtr = Integer.parseInt(tok[i]);
                                     i++;
                                     instantaMea.getObjects().add(intAtr);
                                     /*cast la float + decimalFormat*/
                                } else if(atr.getType().equals("Float")){
                                     float floatAtr = Float.parseFloat(tok[i]);
                                      DecimalFormat df  = new DecimalFormat("#.##");
                                      DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                                      symbols.setDecimalSeparator('.');
                                      df.setDecimalFormatSymbols(symbols);
                                      i++;
                                      instantaMea.getObjects().add(df.format(floatAtr));
                                } else if(atr.getType().equals("String")){
                                    String stringAtr = tok[i];
                                    i++;
                                    instantaMea.getObjects().add(stringAtr);
                                } 
                                /*nodurile in care mai am loc*/
                                int count = 0;
                                for(Nod n : d.getNodes()){
                                    if(  n.getInstances().size()- n.getMaxCapacity()<0)
                                        count ++;
                                }
                                
                                for(Nod n : d.getNodes()){                                  
                                    if(rf == 0)
                                        break;
                                    if(n.getInstances().size() < n.getMaxCapacity() && count >= rf ){
                                        n.getInstances().add(0,instantaMea);
                                        rf--;   
                                    }
                                }
                                    /*construirea nodurilor de care mai am nevoie*/
                                    if( rf > 0){
                                        for(int k = 0; k < rf; k++){
                                        Nod nAdd = new Nod(d.getNoNodes() + k + 1, d.getMaxCapacity());
                                        d.getNodes().add(nAdd);   
                                        }
                                        d.setNoNodes(rf + d.getNoNodes());
                                    }
                                    
                                   for(Nod n : d.getNodes()){                                 
                                    if(rf == 0)
                                        break;
                                    if(n.getInstances().size() < n.getMaxCapacity() ){
                                        n.getInstances().add(0,instantaMea);
                                        rf--;
                                    }
                                }      
                            }    
                        }
                    }  
                }
                /*stergerea unei instante*/
                if(tok[0].equals("DELETE")){
                    int ok = 0;
                    Object objDeSters = null;
                    for(Nod n : d.getNodes()){
                        if(n.getInstances().size() > 0){
                            for(Instanta insParc : n.getInstances()){
                                if(insParc.getName().equals(tok[1]) && insParc.getObjects().get(0).toString().equals(tok[2])){
                                    objDeSters = insParc; ok=1;
                                    break;                                   
                                }
                            }
                            n.getInstances().remove(objDeSters);    
                        }
                    }
                    if(ok == 0)
                        out.println("NO INSTANCE TO DELETE");   
                }
               /*actualizarea instantei*/
                if(tok[0].equals("UPDATE")){  
                    int ok = 0;
                    Instanta obj = new Instanta();/*referinta instantei*/
                    Instanta insMea = new Instanta();/*instanta pe care o adaug dupa update*/   
                for(Entitate entitie : d.getEntities()){
                    int rf = entitie.getrFactor();
                    if(entitie.getName().equals(tok[1])){
                        for( Nod n : d.getNodes()){
                            if(!d.getNodes().isEmpty()){
                                for(Instanta insParc : n.getInstances()){
                                    if(insParc.getName().equals(tok[1]) && insParc.getObjects().get(0).toString().equals(tok[2])){
                                        obj = insParc;
                                        n.getInstances().remove(obj);
                                        ok =1;
                                        break;
                                    }
                                }
                               if(ok == 0)
                                   break;
                                for( int k =0 ; k<entitie.getNoAttributes(); k++){
                                    int t = 3;
                                    if(entitie.getAttributes().get(k).getName().equals(tok[t])){
                                        /*cast la integer*/
                                        if(entitie.getAttributes().get(k).getType().equals("Integer")){
                                            Object r = Integer.parseInt(tok[t+1]);
                                            t+=2;
                                            obj.remove(k);
                                            obj.setI(r, k);
                                            insMea = obj;    
                                            /*cast la float + decimalFormat*/
                                        }else if(entitie.getAttributes().get(k).getType().equals("Float")){
                                                    float r = Float.parseFloat(tok[t+1]);
                                                    DecimalFormat df  = new DecimalFormat("#.##");
                                                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                                                    symbols.setDecimalSeparator('.');
                                                    df.setDecimalFormatSymbols(symbols);
                                                    Object r1 = df.format(r);
                                                    obj.remove(k);
                                                    obj.setI(r1, k);
                                                    insMea = obj;
                                                    t+=2;
                                        }else if(entitie.getAttributes().get(k).getType().equals("String")){
                                                    Object r = tok[t+1];             
                                                    obj.remove(k);
                                                    obj.setI(r, k);
                                                    insMea = obj;
                                                    t+=2;
                                                }
                                    }
                                }       
                            }/*adaug instanta actualizata in nod*/           
                                    if(rf == 0)
                                       break;
                                    int maxC = n.getMaxCapacity(); 
                                       if(n.getInstances().size() < maxC){
                                            n.getInstances().add(0, obj);
                                            rf--;
                                        }
                        }        
                    }
                }   
                }
           
                Object o = new Object();
                /*regasirea instantei*/
                if(tok[0].equals("GET")){
                    int sw =0;
                    Instanta obj = new Instanta();
                    int ok = 0;
                    String afis = new String();
                    for(Nod n : d.getNodes()){
                        for(Instanta insParc : n.getInstances()){
                            if(insParc.getName().equals(tok[1]) && insParc.getObjects().get(0).toString().equals(tok[2])){
                                    obj = insParc;
                                    ok =1;
                                     afis = afis + "Nod" + n.getIdx() + " ";
                            }
                        } 
                    }                    
                    if(ok == 0)
                        out.println("NO INSTANCE FOUND");
                    else{
                        for(Nod n : d.getNodes()){
                            for(Instanta insParc: n.getInstances()){
                                if(obj.getName().equals(insParc.getName()) && sw == 0){
                                    afis = afis + obj.getName()+ " ";
                                    for(Entitate entParc : d.getEntities()){                              
                                        if(obj.getName().equals(entParc.getName())){
                                            for(int j = 0; j < insParc.getObjects().size();j++){
                                                String numeAtr = entParc.getAttributes().get(j).getName();    
                                                afis = afis + numeAtr+":";
                                                o = obj.getObjects().get(j);
                                                if(j == insParc.getObjects().size() - 1)
                                                    afis = afis + o;
                                                else
                                                    afis = afis + o + " ";
                                            }
                                        }
                                    }
                                    sw=1;
                                }
                            }     
                        } 
                       out.println(afis); 
                    }   
                }             
                /*afisarea bazei de date*/
                if(tok[0].equals("SNAPSHOTDB")){ 
                    int count =0 ;
                    String num = new String(); 
                    for(Nod n : d.getNodes()){
                        if(!n.getInstances().isEmpty()){
                            out.println("Nod" + n.getIdx());                       
                        for(Instanta insParc: n.getInstances()){
                            num = insParc.getName()+" ";
                            for(Entitate entParc : d.getEntities()){                              
                                    if(insParc.getName().equals(entParc.getName())){
                                        for(int j = 0; j < insParc.getObjects().size();j++){
                                            String numeAtr = entParc.getAttributes().get(j).getName();    
                                            num = num + numeAtr+":";
                                            o = insParc.getObjects().get(j);
                                            if(j == insParc.getObjects().size() - 1)
                                                num = num + o;
                                            else
                                                num = num + o + " ";
                                        }
                                    }
                            }
                           out.println(num);  
                        }
                    }/*pt cazul cand baza de date are nodurile create insa acestea sunt goale*/
                        else
                            count++; 
                    }
                    if(count == d.getNodes().size() )
                        out.println("EMPTY DB");
                }
                /*eliminarea instantei cu timestamp ul mai mic ca cel dat ca param*/
                if(tok[0].equals("CLEANUP")){
                  long timestamp = Long.parseLong(tok[2]);
                    if(d.getDbName().equals(tok[1])){
                        for(Nod n : d.getNodes()){
                            if(!n.getInstances().isEmpty()){
                                 for(int k = 0; k < n.getInstances().size(); k++){
                                    if(n.getInstances().get(k).getTimestamp() < timestamp){
                                         n.getInstances().remove(k);
                                         k--;   
                                    }
                                }
                            }
                        }
                    }    
                }
                
            }
        } catch(FileNotFoundException ex){
            System.out.println("File not found!");
            System.exit(1);
        }catch(IOException ex){
            System.out.println("nu");
        }
        finally{
            if(reader != null)
            {
                try{
                    reader.close();
                    out.close();
                }catch(IOException ex){
                    System.out.println("nu");
                    System.exit(-1);
                }           
            }
        }
    }
    
}
