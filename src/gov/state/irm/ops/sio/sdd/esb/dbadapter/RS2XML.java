package gov.state.irm.ops.sio.sdd.esb.dbadapter;

import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class RS2XML
{

	String xmlns;
	
	public String getXmlns(){return xmlns;}
	public void setXmlns(String xmlns){this.xmlns = xmlns;}

	/**
	 * 1 based index, [0] is always null.
	 */
	String[] columns;
	String[] types;
	
	void process(ResultSet rs, Writer out) throws IOException, SQLException
	{
		if (columns==null || types==null) initialize(rs);
		out.write("<OutputParameters xmlns='");
		out.write(xmlns);
		out.write("' xmlns:soap-env='http://schemas.xmlsoap.org/soap/envelope/' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>\n<POALLOWIND>Y</POALLOWIND>\n<POVERSION>1.0</POVERSION>\n<COTABLE>\n");
		
		if (rs!=null) while (rs.next())
		{
			for (int i=1; i<columns.length; ++i)
			{
				// fetch each field...
				/*
				 * E.g.
				 * 
				 * <Column name="LOCATION_ID" sqltype="NUMBER">90304</Column>
				 * <Column name="LOCATION_NAME" sqltype="VARCHAR2">Buenos Aires</Column>
				 * <Column name="DOMESTIC_FOREIGN_IND" sqltype="CHAR">F</Column>
				 * <Column name="STATE_TERRITORY_LOCTN_SYS_ID" sqltype="NUMBER" xsi:nil="true"/>
				 * <Column name="REC_STS_EFT_DT" sqltype="DATE">2015-04-08T00:00:00.000-04:00</Column>
				 */
			}
		}
		
		out.write("</COTABLE>\n<POERRMSG xsi:nil='true'/>\n</OutputParameters>\n");
	}

	private void initialize(ResultSet rs)
	{
		if (rs==null) throw new NullPointerException();
		try
		{
			ResultSetMetaData rsmd = rs.getMetaData();
			int counts = rsmd.getColumnCount();
			columns=new String[counts+1];
			types=new String[counts+1];
			
			for (int i=1; i<=counts; ++i)
			{
				columns[i]=rsmd.getColumnName(i);
				types[i]=rsmd.getColumnTypeName(i);
			}
		}
		catch (SQLException e)
		{
			throw new IllegalStateException(e);
		}
	}
}
