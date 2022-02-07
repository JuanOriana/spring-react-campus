import { SectionHeading } from "../../components/generalStyles/utils";
import FileSearcher from "../../components/FileSearcher";
import React, { useEffect, useState } from "react";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../components/generalStyles/pagination";
import FileUnit from "../../components/FileUnit";
import { usePagination } from "../../hooks/usePagination";
import { BigWrapper, GeneralTitle } from "../../components/generalStyles/utils";
import { FileGrid } from "./styles";
import {
  getQueryOrDefault,
  getQueryOrDefaultMultiple,
  useQuery,
} from "../../hooks/useQuery";
import { useNavigate } from "react-router-dom";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { fileService } from "../../services";
import { handleService } from "../../scripts/handleService";
import LoadableData from "../../components/LoadableData";
//

function Files() {
  const { t } = useTranslation();
  const query = useQuery();
  const navigate = useNavigate();
  const [currentPage, pageSize] = usePagination(10);
  const [files, setFiles] = useState(new Array(0));
  const [isLoading, setIsLoading] = useState(false);
  const [maxPage, setMaxPage] = useState(1);
  const [categories, setCategories] = useState(new Array(0));
  const [extensions, setExtensions] = useState(new Array(0));
  const orderDirection = getQueryOrDefault(query, "order-direction", "desc");
  const orderProperty = getQueryOrDefault(query, "order-property", "date");
  const queryStringed = getQueryOrDefault(query, "query", "");
  const extensionTypes = getQueryOrDefaultMultiple(query, "extension-type");
  const categoryTypes = getQueryOrDefaultMultiple(query, "category-type");

  useEffect(() => {
    setIsLoading(true);
    handleService(
      fileService.getFiles(
        categoryTypes.map((type) => parseInt(type)),
        extensionTypes.map((type) => parseInt(type)),
        queryStringed,
        orderProperty,
        orderDirection,
        currentPage,
        pageSize
      ),
      navigate,
      (fileData) => {
        setFiles(fileData ? fileData.getContent() : []);
        setMaxPage(fileData ? fileData.getMaxPage() : 1);
      },
      () => setIsLoading(false)
    );
  }, [currentPage, pageSize]);

  useEffect(() => {
    setCategories([{ categoryName: "Otros", categoryId: 1 }]);
    setExtensions([
      { fileExtensionName: "Otros", fileExtensionId: 1 },
      { fileExtensionName: "Hola", fileExtensionId: 2 },
      { fileExtensionName: "Dos", fileExtensionId: 3 },
    ]);
  }, []);

  return (
    <>
      <SectionHeading>{t("Files.title")}</SectionHeading>
      <BigWrapper>
        <FileSearcher
          orderDirection={orderDirection}
          orderProperty={orderProperty}
          categoryType={categoryTypes}
          query={queryStringed}
          categories={categories}
          extensionType={extensionTypes}
          extensions={extensions}
        />
        <div style={{ display: "flex", alignItems: "center" }}>
          <LoadableData isLoading={isLoading}>
            <FileGrid>
              {files.length === 0 && (
                <GeneralTitle style={{ width: "100%", textAlign: "center" }}>
                  {t("Files.noResults")}
                </GeneralTitle>
              )}
              {files.map((file) => (
                <FileUnit key={file.fileId} isGlobal={true} file={file} />
              ))}
            </FileGrid>
          </LoadableData>
        </div>
      </BigWrapper>
      <PaginationWrapper style={{ alignSelf: "center" }}>
        {currentPage > 1 && (
          <button
            onClick={() => {
              query.set("page", String(currentPage - 1));
              navigate(`/files?${query.toString()}`);
            }}
            style={{ background: "none", border: "none" }}
          >
            <PaginationArrow
              xRotated={true}
              src="/images/page-arrow.png"
              alt={t("BasicPagination.alt.beforePage")}
            />
          </button>
        )}
        {t("BasicPagination.message", {
          currentPage: currentPage,
          maxPage: maxPage,
        })}
        {currentPage < maxPage && (
          <button
            onClick={() => {
              query.set("page", String(currentPage + 1));
              navigate(`/files?${query.toString()}`);
            }}
            style={{ background: "none", border: "none" }}
          >
            <PaginationArrow
              src="/images/page-arrow.png"
              alt={t("BasicPagination.alt.nextPage")}
            />
          </button>
        )}
      </PaginationWrapper>
    </>
  );
}

export default Files;
