/*
 * Copyright (c) 2012-2014, John Campbell and other contributors.  All rights reserved.
 *
 * This file is part of Tectonicus. It is subject to the license terms in the LICENSE file found in
 * the top-level directory of this distribution.  The full list of project contributors is contained
 * in the AUTHORS file found in the same location.
 *
 */

package tectonicus.world;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import tectonicus.cache.swap.Swappable;
import tectonicus.raw.RawSign;
import tectonicus.util.Vector3l;

public class Sign implements Swappable
{
	private int blockId;
	private int blockData;
	
	private Vector3l position;
	
	private String[] text;
	
	public Sign()
	{
		position = new Vector3l();
		text = new String[4];
	}
	
	public Sign(RawSign rawSign)
	{
		blockId = rawSign.blockId;
		blockData = rawSign.blockData;

		position = new Vector3l(rawSign.x, rawSign.y, rawSign.z);
		
		text = new String[4];
		text[0] = rawSign.text1;
		text[1] = rawSign.text2;
		text[2] = rawSign.text3;
		text[3] = rawSign.text4;
	}
	
	public int getData()
	{
		return blockData;
	}

	public long getX()
	{
		return position.x;
	}
	public long getY()
	{
		return position.y;
	}
	public long getZ()
	{
		return position.z;
	}
	
	public String getText(final int index)
	{
		if (index < 0 || index >= text.length)
			return "";
		
		return text[index];
	}
	
	@Override
	public void writeTo(DataOutputStream dest) throws Exception
	{
		dest.writeInt(blockId);
		dest.writeInt(blockData);
		
		dest.writeLong(position.x);
		dest.writeLong(position.y);
		dest.writeLong(position.z);
		
		for (int i=0; i<text.length; i++)
			dest.writeUTF(text[i]);
	}
	
	@Override
	public void readFrom(DataInputStream source) throws Exception
	{
		blockId = source.readInt();
		blockData = source.readInt();
		
		position.x = source.readLong();
		position.y = source.readLong();
		position.z = source.readLong();
		
		for (int i=0; i<text.length; i++)
			text[i] = source.readUTF();
	}
}
