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
import org.mybatis.generator.util.StringUtil;

import java.util.Set;
import java.util.TreeSet;

/**
 * 新增entity方法：
 * xxx addxxx(xxx entity) { return saveAndFetch("insertXXX", user); }
 */
public class InsertBeanMethodGenerator extends AbstractDaoMapperMethodGenerator {

    public void setIntrospectedTable(IntrospectedTable introspectedTable) {
        super.setIntrospectedTable(introspectedTable);
    }

    protected Method generateMethod(Set<FullyQualifiedJavaType> importedTypes) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType entityType = GenUtil.getEntityType(context, introspectedTable);
        method.setReturnType(entityType);
        importedTypes.add(entityType);
        String entityParaName = GenUtil.getGeneralEntityParamName(introspectedTable);

        method.setName(GenUtil.getInsertBeanMethodName(introspectedTable, GenUtil.ENUM_METHOD_TYPE.DAO_TYPE));
        method.addParameter(new Parameter(GenUtil.getEntityType(context, introspectedTable), entityParaName));

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
        sb.append(GenUtil.getInsertBeanMethodName(introspectedTable, GenUtil.ENUM_METHOD_TYPE.XML_TYPE));
        sb.append("\"");
        sb.append(", ");
        sb.append(method.getParameters().get(0).getName());
        sb.append(");");
        method.addBodyLine(sb.toString());

        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}

