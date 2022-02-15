export class HeadersMock {
  private content: { name: string; value: string }[] = [];

  public get(name: string): string {
    let toRet = "";
    this.content.forEach((item) => {
      if (item.name === name) {
        toRet = item.value;
      }
    });
    return toRet;
  }

  public set(name: string, value: string) {
    this.content.push({ name: name, value: value });
  }
}

test("", () => {});
