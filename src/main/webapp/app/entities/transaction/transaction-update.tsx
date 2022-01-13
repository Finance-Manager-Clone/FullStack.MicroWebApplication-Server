import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { IProductAccount } from 'app/shared/model/product-account.model';
import { getEntities as getProductAccounts } from 'app/entities/product-account/product-account.reducer';
import { getEntity, updateEntity, createEntity, reset } from './transaction.reducer';
import { ITransaction } from 'app/shared/model/transaction.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export const TransactionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const categories = useAppSelector(state => state.category.entities);
  const productAccounts = useAppSelector(state => state.productAccount.entities);
  const transactionEntity = useAppSelector(state => state.transaction.entity);
  const loading = useAppSelector(state => state.transaction.loading);
  const updating = useAppSelector(state => state.transaction.updating);
  const updateSuccess = useAppSelector(state => state.transaction.updateSuccess);
  const transactionTypeValues = Object.keys(TransactionType);
  const currencyValues = Object.keys(Currency);
  const handleClose = () => {
    props.history.push('/transaction');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCategories({}));
    dispatch(getProductAccounts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.time = convertDateTimeToServer(values.time);

    const entity = {
      ...transactionEntity,
      ...values,
      category: categories.find(it => it.id.toString() === values.category.toString()),
      from: productAccounts.find(it => it.id.toString() === values.from.toString()),
      to: productAccounts.find(it => it.id.toString() === values.to.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          time: displayDefaultDateTime(),
        }
      : {
          transactionType: 'Debit',
          currency: 'USD',
          ...transactionEntity,
          time: convertDateTimeFromServer(transactionEntity.time),
          category: transactionEntity?.category?.id,
          from: transactionEntity?.from?.id,
          to: transactionEntity?.to?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myApp.transaction.home.createOrEditLabel" data-cy="TransactionCreateUpdateHeading">
            <Translate contentKey="myApp.transaction.home.createOrEditLabel">Create or edit a Transaction</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="transaction-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myApp.transaction.transactionType')}
                id="transaction-transactionType"
                name="transactionType"
                data-cy="transactionType"
                type="select"
              >
                {transactionTypeValues.map(transactionType => (
                  <option value={transactionType} key={transactionType}>
                    {translate('myApp.TransactionType.' + transactionType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('myApp.transaction.amount')}
                id="transaction-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('myApp.transaction.time')}
                id="transaction-time"
                name="time"
                data-cy="time"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('myApp.transaction.currency')}
                id="transaction-currency"
                name="currency"
                data-cy="currency"
                type="select"
              >
                {currencyValues.map(currency => (
                  <option value={currency} key={currency}>
                    {translate('myApp.Currency.' + currency)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="transaction-category"
                name="category"
                data-cy="category"
                label={translate('myApp.transaction.category')}
                type="select"
              >
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.categoryName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="transaction-from" name="from" data-cy="from" label={translate('myApp.transaction.from')} type="select">
                <option value="" key="0" />
                {productAccounts
                  ? productAccounts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.accountNumber}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="transaction-to" name="to" data-cy="to" label={translate('myApp.transaction.to')} type="select">
                <option value="" key="0" />
                {productAccounts
                  ? productAccounts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.accountNumber}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/transaction" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TransactionUpdate;
