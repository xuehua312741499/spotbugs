/*
 * FindBugs - Find Bugs in Java programs
 * Copyright (C) 2006, University of Maryland
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package edu.umd.cs.findbugs.detect;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector2;
import edu.umd.cs.findbugs.asm.ClassNodeDetector;
import edu.umd.cs.findbugs.classfile.CheckedAnalysisException;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.classfile.Global;

/**
 * Sample detector, using ASM
 * @author David Hovemeyer
 */
public class TestASM extends ClassNodeDetector  {
	
	public TestASM(BugReporter bugReporter) {
		super(bugReporter);
	}

	@Override
	public MethodVisitor visitMethod(int access,
            String name,
            String desc,
            String signature, String [] exceptions) {
	BugInstance bug = new BugInstance(this, "TESTING", NORMAL_PRIORITY)
	.addClass(this).addMethod(this.name, name, desc, access).addString("method should start with lower case character");
	bugReporter.reportBug(bug);
	return null;
}
	@Override
	public FieldVisitor visitField(int access,
            String name,
            String desc,
            String signature,
            Object value) {
		if ((access & Opcodes.ACC_STATIC) != 0 && (access & Opcodes.ACC_FINAL) != 0 && (access & Opcodes.ACC_PUBLIC) != 0 
				&& !name.equals(name.toUpperCase()))
			bugReporter.reportBug(new BugInstance(this, "TESTING", Detector2.LOW_PRIORITY)
			.addClass(this).addField(this.name, name, desc, access));
		return null;
	}

}
