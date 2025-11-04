export const setFlashMessage = (msg: string) => {
    sessionStorage.setItem("flash", msg);
}
export const getFlashMessage = () => {
    const msg = sessionStorage.getItem("flash");
    if (msg) sessionStorage.removeItem("flash");
    return msg;
}
