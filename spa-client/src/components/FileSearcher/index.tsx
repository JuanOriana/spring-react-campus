import React, { useState, useEffect } from "react";
import PropTypes, { InferProps } from "prop-types";
import { FormButton, FormInput } from "../generalStyles/form";
import {
  FileFilterContainer,
  FileQueryContainer,
  FileSelect,
  FileSelectLabel,
  FileCheckboxLabel,
  FileCheckbox,
  PaginationArrow,
  FileFilterPill,
} from "./syles";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
//

interface codeToMessage {
  code: string;
  message: string;
}

FileSearcher.propTypes = {
  orderProperty: PropTypes.string,
  orderDirection: PropTypes.string,
  extensions: PropTypes.array.isRequired,
  extensionType: PropTypes.array,
  categories: PropTypes.array.isRequired,
  categoryType: PropTypes.array,
  query: PropTypes.string,
};

FileSearcher.defaultProps = {
  orderProperty: "date",
  orderDirection: "desc",
  query: "",
  extensionType: [],
  categoryType: [],
};

//LOS VALORES NO SE SETTEAN EN BASE A LO QUE ESTA EN LA URL
function FileSearcher({
  orderProperty,
  orderDirection,
  extensions,
  extensionType,
  categories,
  categoryType,
  query,
}: InferProps<typeof FileSearcher.propTypes>) {
  const { t } = useTranslation();
  const orderings: codeToMessage[] = [
    { code: "date", message: "Fecha" },
    { code: "name", message: "Nombre" },
    { code: "downloads", message: "Descargas" },
  ];
  const directions: codeToMessage[] = [
    { code: "asc", message: "Ascendente" },
    { code: "desc", message: "Descendente" },
  ];
  const [isAmplified, setIsAmplified] = useState(false);
  const [catCheckState, setCatCheckState] = useState(new Array(1));
  const [extCheckState, setExtCheckState] = useState(new Array(1));
  const [catAllState, setCatAllState] = useState(false);
  const [extAllState, setExtAllState] = useState(false);

  useEffect(() => {
    setCatAllState(categoryType ? categoryType.includes("-1") : false);
    setExtAllState(extensionType ? extensionType.includes("-1") : false);
    const initCatCheckState = new Array(categories!.length);
    const initExtCheckState = new Array(extensions!.length);
    initCatCheckState.fill(false);
    initExtCheckState.fill(false);

    categoryType!.map((idx) => (initCatCheckState[idx] = true));
    extensionType!.map((idx) => (initExtCheckState[idx] = true));
    setCatCheckState(initCatCheckState);
    setExtCheckState(initExtCheckState);
  }, [categories, extensions, extensionType, categoryType]);

  function toggleFilters(){
    setIsAmplified((lastState) => !lastState);
    const toggler = document.getElementById("filter-toggle");
    if ( isAmplified && toggler != null){
      toggler.style.transform="rotate(90deg)";
    }else if (toggler != null){
      toggler.style.transform="rotate(-90deg)";
    }
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
          placeholder={t("FileSearcher.placeholder.fileName")}
          defaultValue={query ? query : ""}
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
          {t("FileSearcher.search")}
        </FormButton>
        <PaginationArrow
          src="./images/outline-arrow.png"
          style={{ transform: "rotate(90deg)", marginLeft: "10px" }}
          onClick={toggleFilters}
          alt={t("FileSearcher.alt.toggleFilters")}
          id="filter-toggle"
        />
      </div>

      {isAmplified && (
        <FileFilterContainer>
          <div style={{ display: "flex", justifyContent: "space-between" }}>
            <div style={{ display: "flex", flexDirection: "column" }}>
              <FileSelectLabel>
                {t("FileSearcher.searchBy.title")}
              </FileSelectLabel>
              <FileSelect
                name="order-property"
                id="order-property"
                defaultValue={orderProperty ? orderProperty : undefined}
              >
                {orderings.map((ordering) => (
                  <option key={ordering.code} value={ordering.code}>
                    {t("FileSearcher.searchBy." + ordering.code)}
                  </option>
                ))}
              </FileSelect>
              <FileSelectLabel>
                {t("FileSearcher.orderBy.title")}
              </FileSelectLabel>
              <FileSelect
                name="order-direction"
                id="order-direction"
                defaultValue={orderDirection ? orderDirection : undefined}
              >
                {directions.map((direction) => (
                  <option key={direction.code} value={direction.code}>
                    {t("FileSearcher.orderBy." + direction.code)}
                  </option>
                ))}
              </FileSelect>
            </div>

            <div style={{ display: "flex", flexDirection: "column" }}>
              <FileSelectLabel>
                {t("FileSearcher.fileType.title")}
              </FileSelectLabel>
              <span>
                <FileCheckbox
                  type="checkbox"
                  id="extension-all"
                  name="extension-type"
                  value={-1}
                  checked={extAllState}
                  onChange={() => {
                    setExtAllState(!extAllState);
                    const newExtCheckState = [...extCheckState];
                    newExtCheckState.fill(!extAllState);
                    setExtCheckState(newExtCheckState);
                  }}
                />
                <FileCheckboxLabel>
                  {t("FileSearcher.fileType.all")}
                </FileCheckboxLabel>
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
                        checked={extCheckState[index]}
                        onChange={() => {
                          setExtAllState(false);
                          setExtCheckState((lastValue) => {
                            const newArr = [...lastValue];
                            newArr[index] = !newArr[index];
                            return newArr;
                          });
                        }}
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
                  checked={extCheckState[0]}
                  onChange={() => {
                    setExtAllState(false);
                    setExtCheckState((lastValue) => {
                      const newArr = [...lastValue];
                      newArr[0] = !newArr[0];
                      return newArr;
                    });
                  }}
                />
                <FileCheckboxLabel>
                  {t("FileSearcher.fileType.other")}
                </FileCheckboxLabel>
              </span>
            </div>

            <div style={{ display: "flex", flexDirection: "column" }}>
              <FileSelectLabel>
                {t("FileSearcher.category.title")}
              </FileSelectLabel>
              <span>
                <FileCheckbox
                  type="checkbox"
                  id="category-all"
                  name="category-type"
                  value={-1}
                  checked={catAllState}
                  onChange={() => {
                    setCatAllState(!catAllState);
                    const newCatCheckState = [...catCheckState];
                    newCatCheckState.fill(!catAllState);
                    setCatCheckState(newCatCheckState);
                  }}
                />
                <FileCheckboxLabel>
                  {t("FileSearcher.category.all")}
                </FileCheckboxLabel>
              </span>
              {categories!.map((category, index) => (
                <span key={index}>
                  <FileCheckbox
                    type="checkbox"
                    id={`category-${category.categoryId}`}
                    name="category-type"
                    value={category.categoryId}
                    checked={catCheckState[index]}
                    onChange={() => {
                      setCatAllState(false);
                      setCatCheckState((lastValue) => {
                        const newArr = [...lastValue];
                        newArr[index] = !newArr[index];
                        return newArr;
                      });
                    }}
                  />
                  <FileCheckboxLabel>
                    {t("Category." + category.categoryName)}
                  </FileCheckboxLabel>
                </span>
              ))}
            </div>
          </div>
          <FormButton
            type="button"
            style={{ alignSelf: "end" }}
            onClick={() => {
              setExtAllState(false);
              setCatAllState(false);
              setExtCheckState((lastValue) => {
                const newArr = [...lastValue];
                newArr.fill(false);
                return newArr;
              });
              setCatCheckState((lastValue) => {
                const newArr = [...lastValue];
                newArr.fill(false);
                return newArr;
              });
            }}
          >
            {t("FileSearcher.clearFilters")}
          </FormButton>
        </FileFilterContainer>
      )}
      {!isAmplified && (categoryType!.length > 0 || extensionType!.length > 0) && (
        <div
          style={{ display: "flex", alignItems: "center", flexWrap: "wrap" }}
        >
          <FileCheckboxLabel style={{ fontWeight: 700 }}>
            {t("FileSearcher.filteredBy")}
          </FileCheckboxLabel>
          {categoryType!.map((idx) => {
            if (idx === "-1") return false;
            return (
              <FileFilterPill key={idx} red={false}>
                {categories[idx] &&
                  t("Category." + categories[idx]?.categoryName)}
              </FileFilterPill>
            );
          })}
          {extensionType!.map((idx) => {
            if (idx === "-1") return false;
            return (
              <FileFilterPill key={idx} red={true}>
                {idx === 0
                  ? t("FileSearcher.fileType.other")
                  : extensions[idx]?.fileExtensionName}
              </FileFilterPill>
            );
          })}
        </div>
      )}
    </FileQueryContainer>
  );
}
export default FileSearcher;
