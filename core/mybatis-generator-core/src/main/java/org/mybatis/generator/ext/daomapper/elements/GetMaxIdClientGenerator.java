/**
 *    Copyright 2006-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.ext.daomapper.elements;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.util.GenUtil;

import java.util.Set;
import java.util.TreeSet;

/**
 */
public class GetMaxIdClientGenerator extends AbstractDaoMapperMethodGenerator {

    @Override
    public void setIntrospectedTable(IntrospectedTable introspectedTable) {
        super.setIntrospectedTable(introspectedTable);
    }

    protected Method generateMethod(Set<FullyQualifiedJavaType> importedTypes) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        //only one primary key
        if (introspectedTable.getPrimaryKeyColumns().size() != 1) {
            return null;
        }

        method.setReturnType(introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType());
        method.setName(GenUtil.getMaxIdMethodName(introspectedTable, GenUtil.ENUM_METHOD_TYPE.DAO_TYPE));
        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);
        return method;
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = generateMethod(importedTypes);
        if (method == null)
            return;

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    @Override
    public void addTopLevelClassElements(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = generateMethod(importedTypes);
        if (method == null)
            return;

        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        sb.append("return (");
        sb.append(introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName());
        sb.append(")getMaxId(\"");
        sb.append(GenUtil.getMaxIdMethodName(introspectedTable, GenUtil.ENUM_METHOD_TYPE.XML_TYPE));
        sb.append("\"); ");
        method.addBodyLine(sb.toString());

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                    introspectedTable.getPrimaryKeyType());


        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}
