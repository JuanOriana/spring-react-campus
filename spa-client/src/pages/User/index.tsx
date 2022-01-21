import { BigWrapper } from "../../components/generalStyles/utils";
import { useForm } from "react-hook-form";
import { ErrorMessage, FormLabel } from "../../components/generalStyles/form";
import { UserSectionWrapper, UserSectionImg } from "./styles";
import React from "react";

type FormData = {
  image?: FileList;
  lastName: string;
};

const currentUser = {
  name: "Juan",
  surname: "Doe",
  userId: 1,
  username: "jdoe",
  email: "jdoe@itba.edu.ar",
  fileNumber: 60000,
};

function User() {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    reset();
  });

  return (
    <BigWrapper style={{ maxWidth: "800px" }}>
      <UserSectionWrapper>
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <h1 style={{ marginBottom: "15px" }}>
            {currentUser.name} {currentUser.surname}
          </h1>
          <UserSectionImg src={`/user/profile-image/${currentUser.userId}`} />
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
            <FormLabel style={{ margin: 0 }}>Insertar imagen</FormLabel>
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
              <ErrorMessage>La imagen es requerida</ErrorMessage>
            )}
            {errors.image?.type === "size" && (
              <ErrorMessage>La imagen debe ser mas chica que 5 mb</ErrorMessage>
            )}

            <button
              style={{
                borderRadius: "4px",
                padding: "4px",
                fontSize: "18px",
                marginTop: "5px",
              }}
            >
              Actualizar
            </button>
          </form>
        </div>
        <div style={{ display: "flex", flexDirection: "column" }}>
          <p>
            <span style={{ fontWeight: "700" }}>Nombre de usuario: </span>
            {currentUser.username}
          </p>
          <p>
            <span style={{ fontWeight: "700" }}>Email: </span>
            {currentUser.email}
          </p>
          <p>
            <span style={{ fontWeight: "700" }}>Legajo: </span>
            {currentUser.fileNumber}
          </p>
        </div>
      </UserSectionWrapper>
    </BigWrapper>
  );
}

export default User;
