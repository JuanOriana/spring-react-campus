import React from "react";
import PropTypes, { InferProps, number, string } from "prop-types";
import { FormButton, FormInput, FormLabel } from "../form/styles";
import {
  FileFilterContainer,
  FileQueryContainer,
  FileSelect,
  FileSelectLabel,
  FileCheckboxLabel,
  FileCheckbox,
  PaginationArrow,
} from "./syles";

interface codeToMessage {
  code: string;
  message: string;
}

FileSearcher.propTypes = {
  orderProperty: PropTypes.string,
  orderDirection: PropTypes.string,
  extensions: PropTypes.array,
  extensionType: PropTypes.array,
  categories: PropTypes.array,
  categoryType: PropTypes.array,
};

//LOS VALORES NO SE SETTEAN EN BASE A LO QUE ESTA EN LA URL
function FileSearcher({
  orderProperty,
  orderDirection,
  extensions,
  extensionType,
  categories,
  categoryType,
}: InferProps<typeof FileSearcher.propTypes>) {
  const orderings: codeToMessage[] = [
    { code: "date", message: "Fecha" },
    { code: "name", message: "Nombre" },
    { code: "downloads", message: "Descargas" },
  ];
  const directions: codeToMessage[] = [
    { code: "asc", message: "Ascendente" },
    { code: "desc", message: "Descendente" },
  ];

  function arrayEquals(a: any[], b: any[]) {
    return (
      Array.isArray(a) &&
      Array.isArray(b) &&
      a.length === b.length &&
      a.every((val, index) => val === b[index])
    );
  }

  return (
    <FileQueryContainer action="">
      <div
        style={{ display: "flex", alignItems: "center", marginBottom: "10px" }}
      >
        <FormInput
          name="query"
          id="query"
          style={{
            width: "70%",
            height: "30px",
            borderTopRightRadius: 0,
            borderBottomRightRadius: 0,
            border: "none",
            margin: 0,
          }}
          placeholder="Escribe el nombre del archivo"
        />
        <FormButton
          style={{
            height: "100%",
            margin: 0,
            width: "120px",
            borderTopLeftRadius: 0,
            borderBottomLeftRadius: 0,
          }}
        >
          Buscar
        </FormButton>
        <PaginationArrow
          src="/resources/images/outline-arrow.png"
          style={{ transform: "rotate(90deg)", marginLeft: "10px" }}
          alt="encender filtros"
          id="filter-toggle"
        />
      </div>

      <FileFilterContainer style={{ display: "none" }}>
        <div style={{ display: "flex", justifyContent: "space-between" }}>
          <div style={{ display: "flex", flexDirection: "column" }}>
            <FileSelectLabel>Buscar por:</FileSelectLabel>
            <FileSelect name="order-property" id="order-property">
              {orderings.map((ordering) => (
                <option
                  key={ordering.code}
                  value={ordering.code}
                  selected={orderProperty == ordering.code}
                >
                  {ordering.message}
                </option>
              ))}
            </FileSelect>
            <FileSelectLabel>Ordenar de forma</FileSelectLabel>
            <FileSelect name="order-direction" id="order-direction">
              {directions.map((direction) => (
                <option
                  key={direction.code}
                  value={direction.code}
                  selected={orderDirection == direction.code}
                >
                  {direction.message}
                </option>
              ))}
            </FileSelect>
          </div>

          <div style={{ display: "flex", flexDirection: "column" }}>
            <FileSelectLabel>Tipo de archivo</FileSelectLabel>
            <span>
              <FileCheckbox
                type="checkbox"
                id="extension-all"
                name="extension-type"
                value={-1}
                checked={arrayEquals(extensions!, extensionType!)}
              />
              <FileCheckboxLabel>Todo</FileCheckboxLabel>
            </span>
            {extensions!.map((extension, index) => (
              <>
                {index !== 0 && (
                  <span key={index}>
                    <FileCheckbox
                      type="checkbox"
                      id={`extension-${extension.fileExtensionId}`}
                      name="extension-type"
                      value={extension.fileExtensionId}
                      checked={extensionType!.includes(
                        extension.fileExtensionId
                      )}
                    />
                    <FileCheckboxLabel>
                      {extension.fileExtensionName}
                    </FileCheckboxLabel>
                  </span>
                )}
              </>
            ))}
            <span>
              <FileCheckbox
                type="checkbox"
                id={`extension-${extensions![0].fileExtensionId}`}
                name="extension-type"
                value={extensions![0].fileExtensionId}
                checked={extensionType!.includes(
                  extensions![0].fileExtensionId
                )}
              />
              <FileCheckboxLabel>Otros</FileCheckboxLabel>
            </span>
          </div>

          <div style={{ display: "flex", flexDirection: "column" }}>
            <FileSelectLabel>Categoria</FileSelectLabel>
            <span>
              <FileCheckbox
                type="checkbox"
                id="category-all"
                name="category-type"
                value={-1}
                checked={arrayEquals(categories!, categoryType!)}
              />
              <FileCheckboxLabel>Todas</FileCheckboxLabel>
            </span>
            {categories!.map((category, index) => (
              <span key={index}>
                <FileCheckbox
                  type="checkbox"
                  id={`category-${category.categoryId}`}
                  name="category-type"
                  value={category.categoryId}
                  checked={categoryType!.includes(category.categoryId)}
                />
                <FileCheckboxLabel>{category.categoryName}</FileCheckboxLabel>
              </span>
            ))}
          </div>
        </div>
        <FormButton type="button" style={{ alignSelf: "end" }}>
          Limpiar Filtros
        </FormButton>
      </FileFilterContainer>

      {/*  //TODO: HACER*/}
      {/*// <c:if test="${requestScope.filteredCategories.size() > 0 || requestScope.filteredExtensions.size() > 0}">*/}
      {/*// <div id="filter-by-list" style="display: flex; align-items: center; flex-wrap: wrap">*/}
      {/*// <p class="file-checkbox-label" style="font-weight: 700;"><spring:message code="file.search.filtered.by" htmlEscape="true"/> </p>*/}
      {/*// <c:forEach var="category" items="${requestScope.filteredCategories}">*/}
      {/*// <p class="file-filter-pill">*/}
      {/*// <spring:message code="category.${category.categoryName}" htmlEscape="true"/>*/}
      {/*// </p>*/}
      {/*// </c:forEach>*/}
      {/*// <c:forEach var="extension" items="${requestScope.filteredExtensions}">*/}
      {/*// <p class="file-filter-pill-red">*/}
      {/*// <c:if test="${extension.getFileExtensionName()!='other'}">*/}
      {/*// .<c:out value = "${extension.getFileExtensionName()}"/>*/}
      {/*// </c:if>*/}
      {/*// <c:if test="${extension.getFileExtensionName() =='other'}">*/}
      {/*// <spring:message code="file.search.type.other" />*/}
      {/*// </c:if>*/}
      {/*//*/}
      {/*// </p>*/}
      {/*// </c:forEach>*/}
      {/*// </div>*/}
      {/*// </c:if>*/}
    </FileQueryContainer>
  );
}

export default FileSearcher;
