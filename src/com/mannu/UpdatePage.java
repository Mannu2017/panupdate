package com.mannu;

import java.awt.EventQueue;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UpdatePage{
	private Connection con;
	
	public static void main(String [] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				UpdatePage up=new UpdatePage();
			}
		});
	}

	public UpdatePage() {
		try {
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			 con=DriverManager.getConnection("jdbc:sqlserver://192.168.84.90;user=sa;password=Karvy@123;database=pandotnet");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps=con.prepareStatement("select fieldvalue from panfilevalidation where fieldname='Acknowledgement Number/Aadhaar Number/Aadhaar Flag/Birth Year' and upldate>='2017-07-01';");
			ResultSet rs=ps.executeQuery();
			int sl=0;
			while (rs.next()) {
				PreparedStatement ps3=con.prepareStatement("update panfilevalidation set fieldname='1'+fieldname where fieldvalue='"+rs.getString(1)+"' and fieldname='Acknowledgement Number/Aadhaar Number/Aadhaar Flag/Birth Year'");
				ps3.execute();
				ps3.close();  
				sl=1+sl;
				String ackno=null;
				String appdob=null;
				String appfullname=null;
				String appfname=null;
				String appmname=null;
				String applname=null;
				String appgen=null;
				
				String[] ack=rs.getString(1).split(",");
				if (ack.length>0) {
					ack=ack[0].split(":");
					if (ack.length>0) {
						ackno=ack[1];
					}
				}
				String[] dob=rs.getString(1).split("Birth Date:");
				if (dob.length>0) {
					dob=dob[1].split("Aadhaar Name :");
					appdob=dob[0];
				}
				DateFormat df=new SimpleDateFormat("ddMMyyyy");
				DateFormat df1=new SimpleDateFormat("yyyy-MM-dd");
				Date dd=(Date) df.parse(appdob);
				appdob=df1.format(dd);
				String[] fullname=rs.getString(1).split("Aadhaar Name : ");
				if (fullname.length>0) {
					fullname=fullname[1].split(" Gender :");
					appfullname=fullname[0];
					appgen=fullname[1];
				}
				System.out.println("Slno: "+sl+" Ack No: "+ackno);
				PreparedStatement ps1=con.prepareStatement("select s.FIRSTNM,s.MIDDLENM,s.LASTNAME,s.NAMEAAD from SAM010717 s left join panfilevalidation v on s.ACKNO=v.fieldvalue where s.ACKNO='"+ackno+"' and respdesc!='Record inserted succesfully';");
				ResultSet rs1=ps1.executeQuery();
				if (rs1.next()) {
					System.out.println("Slno: "+sl+" Ack No: "+ackno+" DOB: "+appdob+" Full Name: "+appfullname+" Gen: "+appgen);
					if (rs1.getString(1)==null ||rs1.getString(1).equals("") ) {
						appfname="";
					} else {
						appfname=rs1.getString(1);
					}
					
					if (rs1.getString(2)==null ||rs1.getString(2).equals("") ) {
						appmname="";
					} else {
						appmname=rs1.getString(2);
					}
					
					if (rs1.getString(3)==null ||rs1.getString(3).equals("") ) {
						applname="";
					} else {
						applname=rs1.getString(3);
					}
					
					if (rs1.getString(4)==null ||rs1.getString(4).equals("") ) {
						appfullname="";
					} else {
						appfullname=rs1.getString(4);
					}
					
					PreparedStatement ps2=con.prepareStatement("update pandetail set AppFirstName='"+appfname+"',AppMiddleName='"+appmname+"',AppLastName='"+applname+"',AppNameOnCard='"+appfullname+"',AppDOB='"+appdob+" 00:00:00.000',AppSex='"+appgen+"',FileGenerationFlag='B' where AcknowledgeNo='"+ackno+"' and FileGenerationFlag='G'");
					ps2.execute();
					ps2.close();					
				}
				ps1.close();
				rs1.close();
				
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps=con.prepareStatement("select fieldvalue from panfilevalidation where fieldname='Acknowledgement Number/Aadhaar Number/Aadhaar Flag/Birth Year/Birth date/Applicant Name/Gender' and upldate>='2017-07-01';");
			ResultSet rs=ps.executeQuery();
			int sl=0;
			while (rs.next()) {
				PreparedStatement ps3=con.prepareStatement("update panfilevalidation set fieldname='1'+fieldname where fieldvalue='"+rs.getString(1)+"' and fieldname='Acknowledgement Number/Aadhaar Number/Aadhaar Flag/Birth Year/Birth date/Applicant Name/Gender';");
				ps3.execute();
				ps3.close();
				sl=1+sl;
				String ackno=null;
				String appfname=null;
				String appmnam=null;
				String applnam=null;
				String appfulname=null;
				String appgen=null;
				String appdob=null;
				String[] ack=rs.getString(1).split(",");
				if (ack.length>0) {
					ack=ack[0].split(":");
					if (ack.length>0) {
						ackno=ack[1];
					}
				}
				String[] fistn=rs.getString(1).split("First Name :");
				if (fistn.length>0) {
					fistn=fistn[1].split("Middle Name :");
					appfname=fistn[0];
				}
				String[] midn=rs.getString(1).split("Middle Name :");
				if (midn.length>0) {
					midn=midn[1].split("Last Name :");
					appmnam=midn[0];
				}
				String[] lasn=rs.getString(1).split("Last Name :");
				if (lasn.length>0) {
					lasn=lasn[1].split("Gender :");
					applnam=lasn[0];
					appgen=lasn[1];
				}
				String[] dob=rs.getString(1).split("Birth Date:");
				if (dob.length>0) {
					dob=dob[1].split(" First Name :");
					appdob=dob[0];
				}
				DateFormat df=new SimpleDateFormat("ddMMyyyy");
				DateFormat df1=new SimpleDateFormat("yyyy-MM-dd");
				Date dd=(Date) df.parse(appdob);
				appdob=df1.format(dd);
				
				appfulname=appfname+appmnam+applnam;
				System.out.println("Slno: "+sl+"  Ack No: "+ackno);
				
				PreparedStatement ps1=con.prepareStatement("select p.AcknowledgeNo from pandetail p left join panfilevalidation v on p.AcknowledgeNo=v.fieldvalue where p.AcknowledgeNo='"+ackno+"' and p.FileGenerationFlag='G' and respdesc!='Record inserted succesfully'");
				ResultSet rs1=ps1.executeQuery();
				if (rs1.next()) {
					System.out.println("Slno: "+sl+" Ack No: "+ackno+" First Name: "+appfname+" Middle Name: "+appmnam+" Last Name: "+applnam+" Full Name: "+appfulname+" Gender :"+appgen+" DOB: "+appdob);
					
					PreparedStatement ps2=con.prepareStatement("update pandetail set AppLastName='"+applnam+"',AppMiddleName='"+appmnam+"',AppFirstName='"+appfname+"',AppNameOnCard='"+appfulname+"',AppSex='"+appgen+"',AppDOB='"+appdob+" 00:00:00.000',FileGenerationFlag='B' where AcknowledgeNo='"+ackno+"' and FileGenerationFlag='G'");
					ps2.execute();
					ps2.close();
				}
				ps1.close();
				rs1.close();
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			
	}
}
