import { IProductAccount } from 'app/shared/model/product-account.model';

export interface IAccountDetails {
  id?: number;
  firstName?: string;
  lastName?: string;
  phoneNumber?: string;
  email?: string;
  address?: string;
  city?: string;
  country?: string;
  accounts?: IProductAccount[] | null;
}

export const defaultValue: Readonly<IAccountDetails> = {};
