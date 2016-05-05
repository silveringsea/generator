package org.mybatis.generator.ext.daomapper.elements;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

/**
 */
public class InsertBeanMethodGenerator extends AbstractDaoMapperMethodGenerator {

    public void setIntrospectedTable(IntrospectedTable introspectedTable) {
        super.setIntrospectedTable(introspectedTable);
    }

    protected Method generateMethod(Set<FullyQualifiedJavaType> importedTypes) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("add" + introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        //method.addParameter(new Parameter(introspectedTable.getPrimaryKeyType(), "key"));

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);
        return method;
    }

    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = generateMethod(importedTypes);
        if (method == null)
            return;

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    public void addTopLevelClassElements(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = generateMethod(importedTypes);
        if (method == null)
            return;

        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        sb.append("return ");
        sb.append("saveAndFetch(\"");
        sb.append(method.getName());
        sb.append("\"); ");
        sb.append(';');
        method.addBodyLine(sb.toString());

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getPrimaryKeyType());


        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}
