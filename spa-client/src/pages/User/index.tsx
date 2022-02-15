import { BigWrapper } from "../../components/generalStyles/utils";
import { useForm } from "react-hook-form";
import { ErrorMessage, FormLabel } from "../../components/generalStyles/form";
import { UserSectionWrapper, UserSectionImg } from "./styles";
import React, { useEffect, useState } from "react";
import { useAuth } from "../../contexts/AuthContext";
import LoadableData from "../../components/LoadableData";
import { handleService } from "../../scripts/handleService";
import { userService } from "../../services";
import { useNavigate } from "react-router-dom";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { renderToast } from "../../scripts/renderToast";
//

type FormData = {
  image?: FileList;
  lastName: string;
};

function User() {
  const { t } = useTranslation();
  const { user, setUser } = useAuth();
  const navigate = useNavigate();
  const [reload, setReload] = useState(true);
  const [userImg, setUserImg] = useState<string | undefined>(undefined);
  const [isLoadingImg, setIsLoadingImg] = useState(false);
  useEffect(() => {
    setIsLoadingImg(true);
    if (user) {
      handleService(
        userService.getUserProfileImage(user?.userId),
        navigate,
        (userImg) => {
          setUserImg(URL.createObjectURL(userImg));
        },
        () => setIsLoadingImg(false)
      );
    }
  }, [user, reload]);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    userService
      .updateUserProfileImage(user ? user.userId : -1, data.image![0])
      .then((result) => {
        if (!result.hasFailed()) {
          renderToast(t('User.toast.message.changedCorrectly'), "success");
          const imgAsUrl = URL.createObjectURL(data.image![0]);
          setUser({ ...user!, url: imgAsUrl });
          setReload(!reload);
          reset();
        } else {
          renderToast(
              t('User.toast.error.notChanged'),
            "error"
          );
        }
      })
      .catch(() =>
        renderToast(
            t('User.toast.error.notChanged'),
          "error"
        )
      );
  });

  return (
    <BigWrapper style={{ maxWidth: "800px" }}>
      <UserSectionWrapper>
        <LoadableData isLoading={user === null}>
          <div
            style={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <h1 style={{ marginBottom: "15px" }}>
              {user?.name} {user?.surname}
            </h1>
            <LoadableData isLoading={isLoadingImg}>
              <UserSectionImg src={userImg!} />
            </LoadableData>
            <form
              encType="multipart/form-data"
              acceptCharset="utf-8"
              style={{
                margin: "30px 0",
                display: "flex",
                padding: "10px",
                flexDirection: "column",
                border: "2px solid #2EC4B6",
                borderRadius: "12px",
              }}
              onSubmit={onSubmit}
            >
              <FormLabel style={{ margin: 0 }}>
                {t("User.form.title")}
              </FormLabel>
              <input
                type="file"
                accept="image/png, image/jpeg"
                {...register("image", {
                  validate: {
                    required: (image) =>
                      image !== undefined && image[0] !== undefined,

                    size: (image) =>
                      image && image[0] && image[0].size / (1024 * 1024) < 5,
                  },
                })}
              />
              {errors.image?.type === "required" && (
                <ErrorMessage>{t("User.error.file.isRequired")}</ErrorMessage>
              )}
              {errors.image?.type === "size" && (
                <ErrorMessage>{t("User.error.file.size")}</ErrorMessage>
              )}

              <button
                style={{
                  borderRadius: "4px",
                  padding: "4px",
                  fontSize: "18px",
                  marginTop: "5px",
                }}
              >
                {t("User.form.confirmButton")}
              </button>
            </form>
          </div>
          <div style={{ display: "flex", flexDirection: "column" }}>
            <p>
              <span style={{ fontWeight: "700" }}>
                {t("User.usernameTitle")}
              </span>
              {user?.username}
            </p>
            <p>
              <span style={{ fontWeight: "700" }}>{t("User.emailTitle")}</span>
              {user?.email}
            </p>
            <p>
              <span style={{ fontWeight: "700" }}>
                {t("User.fileNumberTitle")}
              </span>
              {user?.fileNumber}
            </p>
          </div>
        </LoadableData>
      </UserSectionWrapper>
    </BigWrapper>
  );
}

export default User;
