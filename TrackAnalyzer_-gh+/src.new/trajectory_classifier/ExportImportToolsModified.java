package trajectory_classifier;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import traJ.TrajectoryModified;


public class ExportImportToolsModified {
	
	public void exportTrajectoryDataAsCSV(ArrayList<? extends TrajectoryModified> tracks, String path){
		String[] nextLine = null;
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(path, false));
			nextLine = new String[]{"ID","X","Y","CLASS"};
			writer.writeNext(nextLine);
			
			for(int i = 0; i < tracks.size(); i++){
				TrajectoryModified t = tracks.get(i);
				for(int j = 0; j < t.size(); j++){
					nextLine = new String[]{""+t.getID(),""+t.get(j).x,""+t.get(j).y,t.getType()};
					writer.writeNext(nextLine);
				}
			}
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<TrajectoryModified> importTrajectoryDataFromCSV(String path){
		ArrayList<TrajectoryModified> tracks = new ArrayList<TrajectoryModified>();
		try {
			CSVReader reader = new CSVReader(new FileReader(path));
			String[] nextLine;
			reader.readNext(); //READ HEADER!
			TrajectoryModified t =null;
			int lastID = -1;
			while ((nextLine = reader.readNext()) != null) {
				int nextID = Integer.parseInt(nextLine[0]) ;
				double nextX = Double.parseDouble(nextLine[1]);
				double nextY = Double.parseDouble(nextLine[2]);
				String nextClass = nextLine[3];
				if(nextID==lastID){
					System.out.println();
					t.add(nextX, nextY, 0);
					lastID=nextID;
				}else{
					if(t!=null){
						tracks.add(t);
					}
					t = new TrajectoryModified(2);
					t.setID(nextID);
					t.setType(nextClass);
					t.add(nextX, nextY, 0);
					lastID = nextID;
				}
		    }
			tracks.add(t);
			reader.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tracks;
		
	}

}
