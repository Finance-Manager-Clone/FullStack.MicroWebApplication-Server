import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAccountDetails } from 'app/shared/model/account-details.model';
import { getEntities as getAccountDetails } from 'app/entities/account-details/account-details.reducer';
import { getEntity, updateEntity, createEntity, reset } from './product-account.reducer';
import { IProductAccount } from 'app/shared/model/product-account.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { AccountType } from 'app/shared/model/enumerations/account-type.model';

export const ProductAccountUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const accountDetails = useAppSelector(state => state.accountDetails.entities);
  const productAccountEntity = useAppSelector(state => state.productAccount.entity);
  const loading = useAppSelector(state => state.productAccount.loading);
  const updating = useAppSelector(state => state.productAccount.updating);
  const updateSuccess = useAppSelector(state => state.productAccount.updateSuccess);
  const accountTypeValues = Object.keys(AccountType);
  const handleClose = () => {
    props.history.push('/product-account');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAccountDetails({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.openingDate = convertDateTimeToServer(values.openingDate);

    const entity = {
      ...productAccountEntity,
      ...values,
      accountDetails: accountDetails.find(it => it.id.toString() === values.accountDetails.toString()),
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
          openingDate: displayDefaultDateTime(),
        }
      : {
          accounType: 'Checking',
          ...productAccountEntity,
          openingDate: convertDateTimeFromServer(productAccountEntity.openingDate),
          accountDetails: productAccountEntity?.accountDetails?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myApp.productAccount.home.createOrEditLabel" data-cy="ProductAccountCreateUpdateHeading">
            <Translate contentKey="myApp.productAccount.home.createOrEditLabel">Create or edit a ProductAccount</Translate>
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
                  id="product-account-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myApp.productAccount.accountNumber')}
                id="product-account-accountNumber"
                name="accountNumber"
                data-cy="accountNumber"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('myApp.productAccount.accounType')}
                id="product-account-accounType"
                name="accounType"
                data-cy="accounType"
                type="select"
              >
                {accountTypeValues.map(accountType => (
                  <option value={accountType} key={accountType}>
                    {translate('myApp.AccountType.' + accountType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('myApp.productAccount.openingDate')}
                id="product-account-openingDate"
                name="openingDate"
                data-cy="openingDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('myApp.productAccount.balance')}
                id="product-account-balance"
                name="balance"
                data-cy="balance"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="product-account-accountDetails"
                name="accountDetails"
                data-cy="accountDetails"
                label={translate('myApp.productAccount.accountDetails')}
                type="select"
              >
                <option value="" key="0" />
                {accountDetails
                  ? accountDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/product-account" replace color="info">
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

export default ProductAccountUpdate;
