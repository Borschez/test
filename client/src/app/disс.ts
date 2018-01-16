import {User} from "./user";

export class Disc {
  id: number;
  name: string;
  description: string;
  posterURL: string;
  owner: User;
  discUser: User;
}
