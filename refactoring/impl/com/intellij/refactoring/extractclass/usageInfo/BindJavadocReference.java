/*
 * User: anna
 * Date: 19-Nov-2008
 */
package com.intellij.refactoring.extractclass.usageInfo;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.refactoring.util.FixableUsageInfo;
import com.intellij.util.IncorrectOperationException;

public class BindJavadocReference extends FixableUsageInfo {
  private String myQualifiedName;
  private String myFieldName;

  public BindJavadocReference(final PsiElement element, final String qualifiedName, final String fieldName) {
    super(element);
    myQualifiedName = qualifiedName;
    myFieldName = fieldName;
  }

  public void fixUsage() throws IncorrectOperationException {
    final PsiElement element = getElement();
    if (element != null && element.isValid()) {
      final Project project = element.getProject();
      final PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(myQualifiedName, GlobalSearchScope.projectScope(project));
      if (psiClass != null) {
        final PsiField field = psiClass.findFieldByName(myFieldName, false);
        if (field != null) {
          final PsiReference reference = element.getReference();
          if (reference != null) {
            reference.bindToElement(field);
          }
        }
      }
    }
  }
}